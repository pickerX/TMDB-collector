package com.pickerx.scache.internal

import android.util.Log
import java.io.*
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.collections.HashMap


class IOManager(
    private val cacheDir: File,
    private val maxSize: Long,
    private val maxCount: Int
) {
    private val cacheSize: AtomicLong = AtomicLong()
    private val cacheCount: AtomicInteger = AtomicInteger()
    private val lastUsageDates: MutableMap<File, Long> =
        Collections.synchronizedMap(HashMap<File, Long>())

    init {
        Thread { calCountAndSize() }.start()
    }

    /**
     * convert Serializable object to byteArray
     */
    fun toByteArray(value: Serializable): ByteArray? {
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(value)
            return baos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                oos?.close()
                baos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun write(hashKey: Int, data: String) {
        val file: File = newFile(hashKey)
        write(file, data)
    }

    fun write(hashKey: Int, data: ByteArray) {
        val file: File = newFile(hashKey)
        write(file, data)
    }

    /**
     * read key's value as String
     */
    fun readAsString(hashKey: Int): String {
        val file = get(hashKey)
        val bytes = read(file)
        return String(bytes, Charset.defaultCharset())
    }

    /**
     * read key's value as byteArray
     */
    fun readAsArray(hashKey: Int): ByteArray {
        val file = get(hashKey)
        return read(file)
    }

    /**
     * exist key-value file
     */
    fun exist(hashKey: Int): Boolean {
        return newFile(hashKey).exists()
    }

    fun remove(hashKey: Int): Boolean {
        val file = get(hashKey)
        return file.delete().also { updateCountAndSizeWhenRemove(file) }
    }

    fun count(): Int = cacheCount.get()

    fun clear() {
        lastUsageDates.clear()
        cacheSize.set(0)
        cacheCount.set(0)
        val files = cacheDir.listFiles()
        files?.forEach { it.delete() }
    }

//    /**
//     * 获取 byte 数据
//     *
//     * @param key
//     * @return byte 数据
//     */
//    fun getAsBinary(key: String?): ByteArray? {
//        var RAFile: RandomAccessFile? = null
//        var removeFile = false
//        return try {
//            val file: File = mCache.get(key)
//            if (!file.exists()) return null
//            RAFile = RandomAccessFile(file, "r")
//            val byteArray = ByteArray(RAFile.length().toInt())
//            RAFile.read(byteArray)
//            if (!Utils.isDue(byteArray)) {
//                Utils.clearDateInfo(byteArray)
//            } else {
//                removeFile = true
//                null
//            }
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//            null
//        } finally {
//            if (RAFile != null) {
//                try {
//                    RAFile.close()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//            if (removeFile) remove(key)
//        }
//    }
    /**
     * read Serializable data
     *
     * @param key
     * @return Serializable object
     */
    fun readAsObject(key: Int): Any? {
        val file = get(key)
        val data: ByteArray = read(file)
        // null when key is not exist
        if (data.isEmpty()) return null

        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        try {
            bais = ByteArrayInputStream(data)
            ois = ObjectInputStream(bais)
            return ois.readObject()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                bais?.close()
                ois?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * read byteArray data
     */
    private fun read(file: File): ByteArray {
        if (!file.exists()) return ByteArray(0)

        val ins = FileInputStream(file)
        val bytes = ins.readBytes()
        ins.close()
        return bytes
    }

    /**
     * write byteArray data
     */
    private fun write(file: File, data: ByteArray) {
        var out: FileOutputStream? = null
        var ok = false
        try {
            out = FileOutputStream(file)
            out.write(data)
            ok = true
        } catch (e: Exception) {
            ok = false
            e.printStackTrace()
        } finally {
            try {
                out?.flush()
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (ok) updateCountAndSizeWhenAdd(file)
        }
    }

    /**
     * write string data
     */
    private fun write(file: File, data: String) {
        var out: BufferedWriter? = null
        var fileWriter: FileWriter? = null
        var ok = false
        try {
            fileWriter = FileWriter(file)
            out = BufferedWriter(fileWriter, 1024)
            out.write(data)
            ok = true
        } catch (e: IOException) {
            ok = false
            e.printStackTrace()
        } finally {
            try {
                out?.flush()
                out?.close()
                fileWriter?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (ok) updateCountAndSizeWhenAdd(file)
        }
    }

    private fun newFile(key: Int): File {
        return File(cacheDir, key.toString())
    }

    private fun calCountAndSize() {
        var size = 0L
        var count = 0
        val cachedFiles = cacheDir.listFiles()
        cachedFiles?.forEach {
            size += it.length()
            count++
            lastUsageDates[it] = it.lastModified()
        }
        cacheSize.set(size)
        cacheCount.set(count)

        Log.d("SCache", "Cache >>>count:${cacheCount.get()}, size:${cacheSize.get()}")
    }

    private fun updateCountAndSizeWhenRemove(file: File) {
        cacheCount.decrementAndGet()
        cacheSize.addAndGet(-file.length())
        lastUsageDates.remove(file)
    }

    private fun updateCountAndSizeWhenAdd(file: File) {
        // check count
        var currentCount = cacheCount.get()
        while (currentCount + 1 > maxCount) {
            val freedSize: Long = removeNext()
            cacheSize.addAndGet(-freedSize)
            currentCount = cacheCount.addAndGet(-1)
        }
        cacheCount.addAndGet(1)

        // check max size
        val valueSize: Long = file.length()
        var currentSize = cacheSize.get()
        while (currentSize + valueSize > maxSize) {
            val freedSize: Long = removeNext()
            currentSize = cacheSize.addAndGet(-freedSize)
        }
        cacheSize.addAndGet(valueSize)

        // update usage date
        val currentTime = System.currentTimeMillis()
        file.setLastModified(currentTime)
        lastUsageDates[file] = currentTime

        Log.d("SCache", "count:${cacheCount.get()}, size:${cacheSize.get()}")
    }

    private fun get(key: Int): File {
        val file = newFile(key)
        val currentTime = System.currentTimeMillis()
        file.setLastModified(currentTime)
        lastUsageDates[file] = currentTime
        return file
    }

    /**
     * remove the longest file never visited
     *
     * @return file length
     */
    private fun removeNext(): Long {
        if (lastUsageDates.isEmpty()) {
            return 0
        }
        var oldestUsage: Long = System.currentTimeMillis()
        var mostLongUsedFile: File? = null
        val entries: Set<Map.Entry<File, Long>> = lastUsageDates.entries
        synchronized(lastUsageDates) {
            for ((key, lastValueUsage) in entries) {
                if (lastValueUsage < oldestUsage) {
                    oldestUsage = lastValueUsage
                    mostLongUsedFile = key
                }
            }
        }

        var fileSize = 0L
        mostLongUsedFile?.let {
            fileSize = it.length()
            if (it.delete()) lastUsageDates.remove(mostLongUsedFile)
        }

        return fileSize
    }

}
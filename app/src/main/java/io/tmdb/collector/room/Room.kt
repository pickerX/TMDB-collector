package io.tmdb.collector.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 *
 * @author: pickerx
 * @date:2021/2/24 10:52 AM
 */


fun <T : RoomDatabase> newRoom(
    appContext: Context,
    clazz: Class<T>,
    databaseName: String,
    onCreated: () -> Unit = {},
    vararg migration: Migration
): T {
    return Room.databaseBuilder(appContext, clazz, databaseName)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                onCreated.invoke()
            }
        })
        .addMigrations(*migration)
        .build()
}

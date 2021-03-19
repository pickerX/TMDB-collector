package io.tmdb.collector.utils

/**
 *
 * @author: pickerx
 * @date:2021/3/1 3:43 PM
 */
class ImageUtils {
    companion object {
        private const val BASE_IMAGE_URI = "https://image.tmdb.org/t/p/w1280"
        private const val ORIGINAL_IMAGE_URI = "https://image.tmdb.org/t/p/original"

        fun original(uri: String) = "$ORIGINAL_IMAGE_URI$uri"

        fun size(uri: String) = "$BASE_IMAGE_URI$uri"
    }
}
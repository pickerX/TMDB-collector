package io.tmdb.collector.data.http

/**
 * image configure data
 *
 * @author: pickerx
 * @date:2020/12/2 3:33 PM
 */
data class ConfigureData(
    val images: ImageConfigure,
    val change_keys: String
) {
    data class ImageConfigure(
        val base_url: String,
        val secure_base_url: String,
        val backdrop_sizes: List<String>,
        val logo_sizes: List<String>,
        val poster_sizes: List<String>,
        val profile_sizes: List<String>,
        val still_sizes: List<String>
    )
}

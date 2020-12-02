package io.tmdb.collector.api

/**
 *
 * @author: pickerx
 * @date:2020/11/16 11:01 AM
 */
object Api {

    private const val BASE_API = "https://api.themoviedb.org"

    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
    private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

    /** v3 version http server */
    fun v3() = "$BASE_API/3/"

    fun getPosterPath(posterPath: String) = BASE_POSTER_PATH + posterPath

    fun getBackdropPath(backdropPath: String?) = BASE_BACKDROP_PATH + backdropPath

    fun getYoutubeVideoPath(videoPath: String) = YOUTUBE_VIDEO_URL + videoPath

    fun getYoutubeThumbnailPath(thumbnailPath: String) =
        "$YOUTUBE_THUMBNAIL_URL$thumbnailPath/default.jpg"


}
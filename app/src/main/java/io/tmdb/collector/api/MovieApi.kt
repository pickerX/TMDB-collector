package io.tmdb.collector.api

import com.skydoves.sandwich.ApiResponse
import io.tmdb.collector.data.http.ResultData
import io.tmdb.collector.data.http.ReviewResult
import io.tmdb.collector.data.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    // @GET("movie/{movie_id}/keywords")
    // fun fetchKeywords(@Path("movie_id") id: Int): Call<KeywordListResponse>

    // @GET("movie/{movie_id}/videos")
    // fun fetchVideos(@Path("movie_id") id: Int): Call<VideoListResponse>

    /**
     * 电影评论
     */
    @GET("movie/{movie_id}/reviews")
    suspend fun fetchReviews(@Path("movie_id") id: Int): Call<ReviewResult>

    /**
     * Get the most newly created movie. This is a live response and will continuously change.
     */
    @GET("movie/latest")
    suspend fun fetchLatest(@Query("movie_id") id: Int): Call<ReviewResult>

    /**
     * Get a list of movies in theatres. This is a release type query that looks for all movies that have a
     * release type of 2 or 3 within the specified date range.
     */
    @GET("movie/now_playing")
    suspend fun fetchNowPlaying(
        @Query("page") page: Int,
        @Query("region") region: String
    ): Call<ReviewResult>

    /**
     *
     */
    @GET("movie/popular")
    suspend fun fetchPopular(
        @Query("page") page: Int = 1,
        @Query("region") region: String
    ): Call<ReviewResult>

    /**
     * Get the top rated movies on TMDb.
     *
     * @param page Specify which page to query.
     * @param region Specify a ISO 3166-1 code to filter release dates. Must be uppercase. pattern: ^[A-Z]{2}$
     */
    @GET("movie/top_rated")
    suspend fun fetchTopRated(
        @Query("page") page: Int = 1,
        @Query("region") region: String
    ): Call<ReviewResult>

    /**
     * Get a list of upcoming movies in theatres. This is a release type query that looks for all movies
     * that have a release type of 2 or 3 within the specified date range.
     *
     * @param page Specify which page to query.
     * @param region Specify a ISO 3166-1 code to filter release dates. Must be uppercase. pattern: ^[A-Z]{2}$
     */
    @GET("movie/upcoming")
    suspend fun fetchUpcoming(
//        @Query("page") page: Int = 1,
//        @Query("region") region: String = ""
    ): ApiResponse<ResultData<Movie>>
}
package io.tmdb.collector.repo

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.me.lib.base.utils.readFromAsset
import io.tmdb.collector.App
import io.tmdb.collector.api.MovieApi
import io.tmdb.collector.data.Movie
import io.tmdb.collector.data.http.ResultData
import io.tmdb.collector.utils.ImageUtils
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 *
 * @author: pickerx
 * @date:2020/11/16 3:12 PM
 */
class MovieRepo constructor(private val movieApi: MovieApi) {

    /**
     * 获取最新的影片
     */
    suspend fun getLatest() = flow {
        FakeApi().getMovie(App.get(), "movie_latest.json")?.let {
            emit(it)
        }
    }

    suspend fun getPopular() = flow {
        FakeApi().getMovie(App.get(), "movie_popular.json")?.let {
            it.results = it.results.subList(0, 5)
            it.results.forEach { movie ->
                movie.backdrop_path = ImageUtils.size(movie.backdrop_path ?: "")
            }
            emit(it)
        }
    }

    suspend fun getUpcoming() = flow {
        FakeApi().getUpcoming(App.get())?.let { it ->
            it.results = it.results.subList(0, 10)
            it.results.forEach { movie ->
                movie.poster_path = ImageUtils.size(movie.poster_path ?: "")
            }
            emit(it)
        }
//        movieApi.fetchUpcoming()
//            .suspendOnSuccess {
//                data?.let {
//                    emit(it)
//                }
//            }
//            .suspendOnFailure {
////                if(this is ApiResponse.Failure.Exception<*>) { }
//                result.unknown()
//                emit(result)
//            }
//            .suspendOnError {
//                result.onError(-1, this.errorBody.toString())
//                emit(result)
//            }
    }

    @Suppress("UNCHECKED_CAST")
    class FakeApi {

        fun getMovie(context: Context, path: String): ResultData<Movie>? {
            val json = readFromAsset(context, path)
            return json?.let {
                JSON.parseObject(json, object : TypeReference<ResultData<Movie>>() {})
            }
        }

        fun getUpcoming(context: Context): ResultData<Movie>? {
            val json = readFromAsset(context, "movie_upcoming.json")
            return json?.let {
                JSON.parseObject(json, object : TypeReference<ResultData<Movie>>() {})
            }
        }
    }

}


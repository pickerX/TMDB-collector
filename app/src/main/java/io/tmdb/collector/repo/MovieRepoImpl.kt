package io.tmdb.collector.repo

import android.util.Log
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import io.tmdb.collector.api.MovieApi
import io.tmdb.collector.data.Movie
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * @author: pickerx
 * @date:2020/11/16 3:12 PM
 */
class MovieRepoImpl constructor(private val movieApi: MovieApi) : MovieRepo {

    /**
     * 获取最新的影片
     */
    override suspend fun getLatest() = flow<List<Movie>> {

    }

    override suspend fun getUpcoming() = flow<List<Movie>> {
        val result = mutableListOf<Movie>()
        movieApi.fetchUpcoming()
            .suspendOnSuccess {
                Timber.e(">>> $data")
//                data?.let {
//                    result.addAll(it.results)
//                    emit(result)
//                }
            }
            .suspendOnFailure {
                emit(result)
            }
            .suspendOnError {
                emit(result)
            }
    }

}
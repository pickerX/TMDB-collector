package io.tmdb.collector.repo

import io.tmdb.collector.data.Movie
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author: pickerx
 * @date:2020/11/16 3:12 PM
 */
interface MovieRepo {

    /**
     * 获取最新的影片
     */
    suspend fun getLatest(): Flow<List<Movie>>

    suspend fun getUpcoming(): Flow<List<Movie>>

}
/**
 * Copyright 2020 bejson.com
 */
package io.tmdb.collector.data

import java.util.*

data class Movie(
    val id: Long = 0,
    val title: String,
    var original_title: String? = null,
    var original_language: String? = null,
    var overview: String? = null,
    var adult: Boolean = false,
    var poster_path: String? = null,
    var release_date: Date? = null,
    var genre_ids: List<Int>? = null,
    var backdrop_path: String? = null,
    var popularity: Double = 0.0,
    var vote_count: Int = 0,
    var video: Boolean = false,
    var vote_average: Double = 0.0,
)
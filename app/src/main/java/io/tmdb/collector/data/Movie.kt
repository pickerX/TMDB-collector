/**
 * Copyright 2020 bejson.com
 */
package io.tmdb.collector.data

import androidx.room.Entity
import com.alibaba.fastjson.annotation.JSONCreator

@Entity(primaryKeys = [("id")])
data class Movie(
    var id: Int,
    var page: Int?,
    var keywords: List<Keyword>?,
    var videos: List<Video>?,
    var reviews: List<Review>?,
    var poster_path: String?,
    var adult: Boolean,
    var overview: String,
    var release_date: String?,
    var genre_ids: List<Int>,
    var original_title: String,
    var original_language: String,
    var title: String,
    var backdrop_path: String?,
    var popularity: Float,
    var vote_count: Int,
    var video: Boolean,
    var vote_average: Float // 评分
) {
    // default constructor for fastJson
    @JSONCreator
    constructor() : this(
        0, null, null, null, null,
        null, false, "", null, emptyList(), "",
        "en-US", "", null, 0F, 0, false, 0F
    )
}
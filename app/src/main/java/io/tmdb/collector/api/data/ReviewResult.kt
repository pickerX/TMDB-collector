package io.tmdb.collector.api.data

import io.tmdb.collector.data.Review

class ReviewResult(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
)

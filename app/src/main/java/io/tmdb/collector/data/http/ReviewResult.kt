package io.tmdb.collector.data.http

import io.tmdb.collector.data.Review

class ReviewResult(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
)

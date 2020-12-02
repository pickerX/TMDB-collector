package io.tmdb.collector.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.me.lib.base.BaseVM
import io.tmdb.collector.data.Movie
import io.tmdb.collector.repo.MovieRepo

class HomeViewModel @ViewModelInject constructor(
    private val movieRepo: MovieRepo
) : BaseVM() {

    private val _upcoming = liveData(apiContext) {
        val data = movieRepo.getUpcoming().asLiveData()
        emitSource(data)
    }

    val upcomingMovies: LiveData<List<Movie>> = _upcoming

}
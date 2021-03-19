package io.tmdb.collector.ui.home

import androidx.core.app.AppLaunchChecker
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.me.lib.base.BaseVM
import io.tmdb.collector.data.Movie
import io.tmdb.collector.data.http.ResultData
import io.tmdb.collector.repo.MovieRepo
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val movieRepo: MovieRepo
) : BaseVM() {

    private val _upcoming = liveData(apiContext) {
        val data = movieRepo.getUpcoming()
        emitSource(data.asLiveData())
    }

    val upcomingMovies: LiveData<ResultData<Movie>> = _upcoming

    private val popular = liveData(apiContext) {
        val data = movieRepo.getPopular()
        emitSource(data.asLiveData())
    }

    val popularMovies: LiveData<ResultData<Movie>> = popular

}
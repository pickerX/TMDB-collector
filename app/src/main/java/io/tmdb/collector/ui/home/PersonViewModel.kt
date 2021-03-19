package io.tmdb.collector.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.me.lib.base.BaseVM
import io.tmdb.collector.data.Person
import io.tmdb.collector.data.http.ResultData
import io.tmdb.collector.repo.PersonRepo

/**
 *
 * @author: pickerx
 * @date:2021/3/3 11:35 AM
 */
class PersonViewModel @ViewModelInject constructor(
    private val personRepo: PersonRepo
) : BaseVM() {

    private val _popular = liveData(apiContext) {
        val data = personRepo.getPopular()
        emitSource(data.asLiveData())
    }

    val popularPerson: LiveData<ResultData<Person>> = _popular

}
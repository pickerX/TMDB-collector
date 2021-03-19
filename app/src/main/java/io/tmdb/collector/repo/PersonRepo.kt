package io.tmdb.collector.repo

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.me.lib.base.utils.readFromAsset
import io.tmdb.collector.App
import io.tmdb.collector.api.PersonApi
import io.tmdb.collector.data.Person
import io.tmdb.collector.data.http.ResultData
import io.tmdb.collector.utils.ImageUtils
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 *
 * @author: pickerx
 * @date:2021/3/3 11:18 AM
 */
class PersonRepo constructor(private val api: PersonApi) {

    suspend fun getPopular() = flow {
        FakeApi().getPopular(App.get())?.let { it ->
            it.results = it.results.subList(0, 5)
            it.results.forEach { person ->
                person.profile_path = ImageUtils.original(person.profile_path ?: "")
                Timber.e(">>>$person")
            }
            emit(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class FakeApi {
        fun getPopular(context: Context): ResultData<Person>? {
            val json = readFromAsset(context, "person_popular.json")
            return json?.let {
                JSON.parseObject(json, object : TypeReference<ResultData<Person>>() {})
            }
        }
    }
}
package io.tmdb.collector

import android.content.IntentSender
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope

/**
 *
 * @author: pickerx
 * @date:2021/3/15 10:00 AM
 */
class HomeActivity : ComponentActivity(R.layout.activity_main) {
    init {
        lifecycleScope.launchWhenCreated {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}
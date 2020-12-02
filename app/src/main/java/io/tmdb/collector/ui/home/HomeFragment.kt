package io.tmdb.collector.ui.home

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.me.lib.base.BaseFragment
import com.me.lib.ui.recycler.MuxAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.tmdb.collector.R
import io.tmdb.collector.databinding.FragmentHomeBinding
import io.tmdb.collector.ui.home.item.ViewPagerItem
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun layout(): Int = R.layout.fragment_home

    private val homeViewModel by viewModels<HomeViewModel>()

    private val recyclerView = lazy { binding.includeRecycler.recyclerView }

    private val adapter = lazy { MuxAdapter() }

    private val viewPagerItem = ViewPagerItem()

    override fun onViewsReady() {
        setActionBar(binding.includeToolbar.toolBar)

        recyclerView.value.adapter = adapter.value
        adapter.value.addItem(viewPagerItem)

        homeViewModel.upcomingMovies.observe(this) { data ->
            if (data.results.isEmpty()) {
                Toast.makeText(requireContext(), "No upcoming movies", Toast.LENGTH_LONG).show()
            } else {
                data.results.forEach {
                    Timber.d("upcoming >> ${it.title}")
                }
            }
        }
    }
}
package io.tmdb.collector.ui.home

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.lib.base.BaseFragment
import com.me.lib.base.utils.WindowExt
import com.me.lib.ui.EricImageView
import com.me.lib.ui.recycler.adapter.MuxAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.tmdb.collector.R
import io.tmdb.collector.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.view.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun layout(): Int = R.layout.fragment_home

    private val homeViewModel by viewModels<HomeViewModel>()
    private val personViewModel by viewModels<PersonViewModel>()

    private lateinit var adapter: MuxAdapter
    private lateinit var itemManager: RvItemManager

    override fun onViewsReady() {
        setActionBar(binding.appBarLayout.tool_bar)
        adapter = MuxAdapter()
        itemManager = RvItemManager(adapter).also { it.load() }

        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        homeViewModel.popularMovies.observe(this) { data ->
            if (data.results.isEmpty()) {
                Toast.makeText(requireContext(), "No upcoming movies", Toast.LENGTH_LONG).show()
            } else {
                itemManager.bindPopularMovies(data.results)
            }
        }

        personViewModel.popularPerson.observe(this) { data ->
            itemManager.bindPopularPerson(data.results)
        }

        homeViewModel.upcomingMovies.observe(this) { data ->
            itemManager.bindUpcomingMovies(data.results)
            itemManager.bindTopRatedMovies(data.results)
        }
    }


}
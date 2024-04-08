package com.leperlier.quinquis.lentz.imdb.ui.menu.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentHomeBinding
import com.gmail.eamosse.imdb.databinding.FragmentTrendingBinding
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.MovieDesignType
import com.leperlier.quinquis.lentz.imdb.data.Serie
import com.leperlier.quinquis.lentz.imdb.ui.MovieAdapter
import com.leperlier.quinquis.lentz.imdb.ui.movieDetail.MovieDetailFragment
import com.leperlier.quinquis.lentz.imdb.ui.serieDetail.SerieDetailFragment
import com.leperlier.quinquis.lentz.imdb.ui.serieList.SerieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingFragment : Fragment() {

    private lateinit var binding: FragmentTrendingBinding
    private val trendingViewModel: TrendingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()

        trendingViewModel.trendingDayMovies.observe(viewLifecycleOwner) { trendingDayMovies ->
            (binding.trendingDayMovieList.adapter as MovieAdapter).updateMovies(trendingDayMovies)
        }
        trendingViewModel.trendingWeekMovies.observe(viewLifecycleOwner) { trendingWeekMovies ->
            (binding.trendingWeekMovieList.adapter as MovieAdapter).updateMovies(trendingWeekMovies)
        }


        trendingViewModel.trendingDaySeries.observe(viewLifecycleOwner) { trendingDaySeries ->
            (binding.trendingDaySerieList.adapter as SerieAdapter).updateSeries(trendingDaySeries)
        }
        trendingViewModel.trendingWeekSeries.observe(viewLifecycleOwner) { trendingWeekSeries ->
            (binding.trendingWeekSerieList.adapter as SerieAdapter).updateSeries(trendingWeekSeries)
        }


    }

    private fun setupRecyclerViews() {
        binding.trendingDayMovieList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MovieAdapter(listOf(), { movie ->
                openMovieDetailFragment(movie)
            }, MovieDesignType.HORIZONTAL)
        }

        binding.trendingWeekMovieList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MovieAdapter(listOf(), { movie ->
                openMovieDetailFragment(movie)
            }, MovieDesignType.HORIZONTAL)
        }

        binding.trendingDaySerieList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = SerieAdapter(listOf(), { serie ->
                openSerieDetailFragment(serie)
            }, MovieDesignType.HORIZONTAL)
        }

        binding.trendingWeekSerieList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = SerieAdapter(listOf(), { serie ->
                openSerieDetailFragment(serie)
            }, MovieDesignType.HORIZONTAL)
        }
    }

    private fun openMovieDetailFragment(movie: Movie) {
        val movieDetailFragment = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("movie", movie)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, movieDetailFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openSerieDetailFragment(serie: Serie) {
        val serieDetailFragment = SerieDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("serie", serie)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, serieDetailFragment)
            .addToBackStack(null)
            .commit()
    }


}

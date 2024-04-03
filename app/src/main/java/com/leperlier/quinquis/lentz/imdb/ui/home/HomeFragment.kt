package com.leperlier.quinquis.lentz.imdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentHomeBinding
import com.gmail.eamosse.imdb.ui.home.HomeViewModel
import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.ui.MovieAdapter
import com.leperlier.quinquis.lentz.imdb.ui.movieDetail.MovieDetailFragment
import com.leperlier.quinquis.lentz.imdb.ui.movieList.MovieListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()

        with(homeViewModel) {
            token.observe(viewLifecycleOwner, Observer {
                getCategories()
                getDayTrendingMovies()
                getWeekTrendingMovies()
            })

            categories.observe(viewLifecycleOwner, Observer { categories ->
                binding.categoryList.adapter = CategoryAdapter(categories) { category ->
                    loadMovieListFragment(category)
                }
            })

            trendingDayMovies.observe(viewLifecycleOwner, Observer { trendingDayMovies ->
                (binding.trendingDayMovieList.adapter as MovieAdapter).updateMovies(trendingDayMovies)
            })

            trendingWeekMovies.observe(viewLifecycleOwner, Observer { weekTrendingMovies ->
                (binding.trendingWeekMovieList.adapter as MovieAdapter).updateMovies(weekTrendingMovies)
            })

            error.observe(viewLifecycleOwner, Observer {
                // Afficher l'erreur
            })
        }
    }

    private fun setupRecyclerViews() {
        binding.trendingDayMovieList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.trendingDayMovieList.adapter = MovieAdapter(listOf()) { movie ->
            openMovieDetailFragment(movie)
        }

        binding.trendingWeekMovieList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.trendingWeekMovieList.adapter = MovieAdapter(listOf()) { movie ->
            openMovieDetailFragment(movie)
        }
    }

    private fun setupTrendingMoviesRecyclerView(trendingMovies: List<Movie>) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.trendingDayMovieList.layoutManager = layoutManager

        val moviesAdapter = MovieAdapter(trendingMovies) { movie ->
            openMovieDetailFragment(movie)
        }
        binding.trendingWeekMovieList.adapter = moviesAdapter
    }
    private fun openMovieDetailFragment(movie: Movie) {
        val fragment = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("movie", movie)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }


    fun loadMovieListFragment(category: Category) {
        val fragment =
            MovieListFragment()
        val bundle = Bundle().apply {
            putString("categoryName", category.name)
            putInt("categoryId", category.id)
        }
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack("goToMovieList")
            .commit()
    }
}

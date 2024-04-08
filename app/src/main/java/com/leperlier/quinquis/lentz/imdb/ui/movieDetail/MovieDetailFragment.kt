package com.leperlier.quinquis.lentz.imdb.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gmail.eamosse.imdb.databinding.FragmentMovieDetailBinding
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.MovieDesignType
import com.leperlier.quinquis.lentz.imdb.data.Provider
import com.leperlier.quinquis.lentz.imdb.ui.MovieAdapter
import com.leperlier.quinquis.lentz.imdb.ui.movieList.MovieListViewModel
import com.leperlier.quinquis.lentz.imdb.ui.providers.ProvidersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    private val binding get() = _binding!!

    private val basicUrl = "https://image.tmdb.org/t/p/original"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val movie: Movie? = arguments?.getParcelable("movie")
        movie?.let {
            setupMovieDetails(it)
            movieDetailViewModel.getMovieProviders(it.id)
        }

        movieDetailViewModel.providers.observe(viewLifecycleOwner) { providers ->
            setupProvidersRecyclerView(providers)
        }

        with(movieDetailViewModel) {
            token.observe(viewLifecycleOwner, Observer {
                getSimilarMovies(movie?.id ?: 0)
            })

            similarMovies.observe(viewLifecycleOwner, Observer { moviesList ->
                binding.similarMovieRecyclerView.adapter = MovieAdapter(moviesList, { movie ->
                    val movieDetailFragment = MovieDetailFragment().apply {
                        arguments = Bundle().apply {
                            putParcelable("movie", movie)
                        }
                    }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(com.gmail.eamosse.imdb.R.id.container, movieDetailFragment)
                        .addToBackStack("goToMovieDetails")
                        .commit()
                }, MovieDesignType.VERTICAL)
            })

            error.observe(viewLifecycleOwner, Observer {
            })
        }

        movie?.id?.let { movieId ->
            movieDetailViewModel.getMovieProviders(movieId)
        }
    }

    private fun setupProvidersRecyclerView(providers: List<Provider>) {
        val providersAdapter = ProvidersAdapter(providers)
        binding.streamingServicesRecyclerView.apply {
            adapter = providersAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
    private fun setupMovieDetails(movie: Movie) {
        binding.apply {
            if (movie.backdrop_path != null) {
                loadImageWithGlide(basicUrl + movie.backdrop_path)
            }
            movieTitle.text = movie.title
            movieOverview.text = movie.overview
            movieReleaseDate.text = movie.release_date
            movieVoteAverage.text = movie.vote_average.toString() + " / 10"
            buttonBack.setOnClickListener{
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    fun loadImageWithGlide(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.moviePoster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

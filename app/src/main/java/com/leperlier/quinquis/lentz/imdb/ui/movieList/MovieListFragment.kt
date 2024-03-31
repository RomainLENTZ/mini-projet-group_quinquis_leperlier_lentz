package com.leperlier.quinquis.lentz.imdb.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentMovieListBinding
import com.leperlier.quinquis.lentz.imdb.ui.MovieAdapter
import com.leperlier.quinquis.lentz.imdb.ui.movieDetail.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val movieListViewModel: MovieListViewModel by viewModels()
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private var categoryId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getInt("categoryId")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(context)

        with(movieListViewModel) {
            token.observe(viewLifecycleOwner, Observer {
                getMoviesByCategory(categoryId ?: 0)
            })

            movies.observe(viewLifecycleOwner, Observer { moviesList ->
                binding.moviesRecyclerView.adapter = MovieAdapter(moviesList) { movie ->
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
            })

            error.observe(viewLifecycleOwner, Observer {
            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

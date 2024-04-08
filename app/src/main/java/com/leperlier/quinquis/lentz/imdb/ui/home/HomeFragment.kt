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
import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.ui.movieList.MovieListFragment
import com.leperlier.quinquis.lentz.imdb.ui.serieList.SerieListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuration initiale des RecyclerViews pour les catégories
        setupCategoryRecyclerView()
        setupSerieCategoryRecyclerView()

        homeViewModel.token.observe(viewLifecycleOwner, Observer {
            homeViewModel.getMovieCategories()
            homeViewModel.getSerieCategories()
        })

        homeViewModel.movieCategories.observe(viewLifecycleOwner, Observer { categories ->
            (binding.categoryList.adapter as MovieHorizontalAdapter).updateCategories(categories)
        })

        homeViewModel.serieCategories.observe(viewLifecycleOwner, Observer { categories ->
            (binding.categorySerieList.adapter as SerieHorizontalAdapter).updateCategories(categories)
        })

        homeViewModel.error.observe(viewLifecycleOwner, Observer {
            // Afficher l'erreur
        })
    }

    private fun setupCategoryRecyclerView() {
        binding.categoryList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryList.adapter = MovieHorizontalAdapter(listOf()) { category ->
            loadMovieListFragment(category)
        }
    }

    private fun setupSerieCategoryRecyclerView() {
        binding.categorySerieList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.categorySerieList.adapter = SerieHorizontalAdapter(listOf()) { category ->
            loadSerieListFragment(category)
        }
    }

    private fun loadSerieListFragment(category: Category) {
        val fragment = SerieListFragment().apply {
            arguments = Bundle().apply {
                putString("categoryName", category.name)
                putInt("categoryId", category.id)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun loadMovieListFragment(category: Category) {
        val fragment = MovieListFragment().apply {
            arguments = Bundle().apply {
                putString("categoryName", category.name)
                putInt("categoryId", category.id)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

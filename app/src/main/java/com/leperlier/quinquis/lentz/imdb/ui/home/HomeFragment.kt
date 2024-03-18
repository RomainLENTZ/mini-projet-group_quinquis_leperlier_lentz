package com.leperlier.quinquis.lentz.imdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentHomeBinding
import com.gmail.eamosse.imdb.ui.home.HomeViewModel
import com.leperlier.quinquis.lentz.imdb.data.Category
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

        with(homeViewModel) {
            token.observe(viewLifecycleOwner, Observer {
                getCategories()
            })

            categories.observe(viewLifecycleOwner, Observer {
                binding.categoryList.adapter = CategoryAdapter(it, {
                    loadMovieListFragment(it)
                })
            })

            error.observe(viewLifecycleOwner, Observer {
                //afficher l'erreur
            })
        }
    }

    fun loadMovieListFragment(category: Category){
        val fragment = MovieListFragment() // Assurez-vous de créer une instance de votre MovieListFragment
        // Passez des données à MovieListFragment si nécessaire, par exemple via un Bundle
        val bundle = Bundle().apply {
            putString("categoryName", category.name)
            putInt("categoryId", category.id)
        }
        fragment.arguments = bundle

        // Réalisez la transaction de fragment
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment) // Remplacez 'R.id.container' par l'ID de votre conteneur de fragments
            .addToBackStack(null) // Ajoutez cette transaction à la pile arrière si vous voulez permettre à l'utilisateur de revenir en arrière
            .commit()
    }
}

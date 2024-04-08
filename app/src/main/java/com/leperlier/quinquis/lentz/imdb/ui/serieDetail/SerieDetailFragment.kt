package com.leperlier.quinquis.lentz.imdb.ui.serieDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentSerieDetailBinding
import com.leperlier.quinquis.lentz.imdb.data.MovieDesignType
import com.leperlier.quinquis.lentz.imdb.data.Serie
import com.leperlier.quinquis.lentz.imdb.ui.MovieAdapter
import com.leperlier.quinquis.lentz.imdb.ui.movieDetail.MovieDetailFragment
import com.leperlier.quinquis.lentz.imdb.ui.serieList.SerieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SerieDetailFragment : Fragment() {

    private var _binding: FragmentSerieDetailBinding? = null

    private val serieDetailViewModel: SerieDetailViewModel by viewModels()

    private val binding get() = _binding!!

    private val basicUrl = "https://image.tmdb.org/t/p/original"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSerieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val serie: Serie? = arguments?.getParcelable("serie")
        serie?.let {
            binding.apply {
                if (it.backdrop_path != null) {
                    loadImageWithGlide(basicUrl + it.backdrop_path)
                }
                serieTitle.text = it.name
                serieOverview.text = it.overview
                serieVoteAverage.text = it.vote_average.toString() + " / 10"
                buttonBack.setOnClickListener{
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

        with(serieDetailViewModel) {
            token.observe(viewLifecycleOwner, Observer {
                getSimilarSeries(serie?.id ?: 0)
            })

            similarSeries.observe(viewLifecycleOwner, Observer { serieList ->
                binding.similarSeriesRecyclerView.adapter = SerieAdapter(serieList, { serie ->
                    val serieDetailFragment = SerieDetailFragment().apply {
                        arguments = Bundle().apply {
                            putParcelable("serie", serie)
                        }
                    }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, serieDetailFragment)
                        .addToBackStack("goToSerieDetails")
                        .commit()
                }, MovieDesignType.VERTICAL)
            })

            error.observe(viewLifecycleOwner, Observer {
            })
        }
    }

    fun loadImageWithGlide(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.seriePoster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

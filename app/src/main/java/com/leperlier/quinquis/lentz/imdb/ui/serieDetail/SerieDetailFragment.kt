package com.leperlier.quinquis.lentz.imdb.ui.serieDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.leperlier.quinquis.lentz.imdb.local.entities.FavoriteEntity
import com.leperlier.quinquis.lentz.imdb.ui.MovieAdapter
import com.leperlier.quinquis.lentz.imdb.ui.home.HomeFragment
import com.leperlier.quinquis.lentz.imdb.ui.movieDetail.MovieDetailFragment
import com.leperlier.quinquis.lentz.imdb.ui.serieList.SerieAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SerieDetailFragment : Fragment() {

    private var _binding: FragmentSerieDetailBinding? = null

    private val serieDetailViewModel: SerieDetailViewModel by viewModels()
    private var isCurrentFavorite: Boolean = false

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
        val fromHome: Boolean? = arguments?.getBoolean("fromHome", false)

        serie?.let {
            serieDetailViewModel.getSerieTrailers(it.id.toLong())

            binding.apply {
                if (it.backdrop_path != null) {
                    loadImageWithGlide(basicUrl + it.backdrop_path)
                }

                serieTitle.text = it.name
                serieOverview.text = it.overview
                serieVoteAverage.text = it.vote_average.toString() + " / 10"
                buttonBack.setOnClickListener{
                    if(fromHome == true){
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.container, HomeFragment())
                            .addToBackStack("backToHome")
                            .commit()
                    }else{
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }

            serieDetailViewModel.isFavorite(it.id).observe(viewLifecycleOwner) { isFavorite ->
                isCurrentFavorite = isFavorite
                if (isFavorite) {
                    binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
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

        serieDetailViewModel.trailers.observe(viewLifecycleOwner, Observer { videos ->
            val trailer = videos.find { it.type == "Trailer" }
            if (trailer != null) {
                setupYoutubePlayer(trailer.key)
            } else {
                displayNoTrailersMessage()
            }
        })

        binding.favoriteButton.setOnClickListener {
            serie?.let { serie ->
                val favorite = FavoriteEntity(serie.id.toLong(), serie.name, false)
                if (isCurrentFavorite) {
                    serieDetailViewModel.removeFavorite(favorite)
                } else {
                    serieDetailViewModel.addFavorite(favorite)
                }
            }
        }
    }

    fun loadImageWithGlide(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.seriePoster)
    }

    override fun onDestroyView() {
        if (_binding != null) {
            binding.youtubePlayerView.release()
        }
        _binding = null
        super.onDestroyView()
    }

    private fun setupYoutubePlayer(videoId: String) {
        Log.d("YouTubePlayer", "Loading video ID: $videoId")

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
                youTubePlayer.play()
            }
        })
    }
    private fun displayNoTrailersMessage() {
        binding.noTrailersTextview.visibility = View.VISIBLE
        binding.youtubePlayerView.visibility = View.GONE
    }

}

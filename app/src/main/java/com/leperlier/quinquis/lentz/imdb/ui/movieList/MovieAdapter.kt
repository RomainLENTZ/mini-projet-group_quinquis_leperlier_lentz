package com.leperlier.quinquis.lentz.imdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.databinding.MovieHorizontalItemBinding
import com.gmail.eamosse.imdb.databinding.MovieVerticalItemBinding
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.MovieDesignType

class MovieAdapter(private var movies: List<Movie>,
                   private val onItemClick: (Movie) -> Unit,
                   private val designType: MovieDesignType) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MovieViewHolderHorizontal(val binding: MovieHorizontalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onItemClick: (Movie) -> Unit) {
            binding.movieName.text = movie.title
            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    class MovieViewHolderVertical(val binding: MovieVerticalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onItemClick: (Movie) -> Unit) {
            binding.movieName.text = movie.title
            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (designType) {
            MovieDesignType.HORIZONTAL -> {
                val binding = MovieHorizontalItemBinding.inflate(inflater, parent, false)
                MovieViewHolderHorizontal(binding)
            }
            MovieDesignType.VERTICAL -> {
                val binding = MovieVerticalItemBinding.inflate(inflater, parent, false)
                MovieViewHolderVertical(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movies[position]
        when (holder) {
            is MovieViewHolderHorizontal -> holder.bind(movie, onItemClick)
            is MovieViewHolderVertical -> holder.bind(movie, onItemClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return designType.ordinal
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movies.size
}
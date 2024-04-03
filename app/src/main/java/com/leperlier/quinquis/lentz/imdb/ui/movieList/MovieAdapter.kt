package com.leperlier.quinquis.lentz.imdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.databinding.MovieListItemBinding
import com.leperlier.quinquis.lentz.imdb.data.Movie

class MovieAdapter(private var movies: List<Movie>, private val onItemClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, onItemClick: (Movie) -> Unit) {
            binding.movieName.text = movie.title
            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieListItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, onItemClick)
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movies.size
}

package com.leperlier.quinquis.lentz.imdb.ui.serieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.databinding.SerieHorizontalItemBinding
import com.gmail.eamosse.imdb.databinding.SerieVerticalItemBinding
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.MovieDesignType
import com.leperlier.quinquis.lentz.imdb.data.Serie

class SerieAdapter(private var series: List<Serie>,
                   private val onItemClick: (Serie) -> Unit,
                   private val designType: MovieDesignType) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SerieViewHolderHorizontal(val binding: SerieHorizontalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(serie: Serie, onItemClick: (Serie) -> Unit) {
            binding.name.text = serie.name
            binding.root.setOnClickListener {
                onItemClick(serie)
            }
        }
    }

    class SerieViewHolderVertical(val binding: SerieVerticalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(serie: Serie, onItemClick: (Serie) -> Unit) {
            binding.serieName.text = serie.name
            binding.root.setOnClickListener {
                onItemClick(serie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (designType) {
            MovieDesignType.HORIZONTAL -> {
                val binding = SerieHorizontalItemBinding.inflate(inflater, parent, false)
                SerieViewHolderHorizontal(binding)
            }
            MovieDesignType.VERTICAL -> {
                val binding = SerieVerticalItemBinding.inflate(inflater, parent, false)
                SerieViewHolderVertical(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val serie = series[position]
        when (holder) {
            is SerieViewHolderHorizontal -> holder.bind(serie, onItemClick)
            is SerieViewHolderVertical -> holder.bind(serie, onItemClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return designType.ordinal
    }

    fun updateSeries(series: List<Serie>) {
        this.series = series
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = series.size
}
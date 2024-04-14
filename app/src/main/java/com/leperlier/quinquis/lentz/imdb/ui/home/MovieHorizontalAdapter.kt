package com.leperlier.quinquis.lentz.imdb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.databinding.MovieHorizontalItemBinding
import com.leperlier.quinquis.lentz.imdb.data.Authors
import com.leperlier.quinquis.lentz.imdb.data.Category

class MovieHorizontalAdapter(private var items: List<Category>, private val onItemClick: (Category) -> Unit) :
    RecyclerView.Adapter<MovieHorizontalAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: MovieHorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick.invoke(items[adapterPosition])
            }
        }
        fun bind(item: Category) {
            binding.movieName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(MovieHorizontalItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateCategories(newCategories: List<Category>) {
        items = newCategories
        notifyDataSetChanged()
    }

}
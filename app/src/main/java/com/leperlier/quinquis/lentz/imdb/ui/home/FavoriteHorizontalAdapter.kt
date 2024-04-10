package com.leperlier.quinquis.lentz.imdb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.databinding.FavoriteHorizontalItemBinding
import com.gmail.eamosse.imdb.databinding.SerieHorizontalItemBinding
import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.local.entities.FavoriteEntity

class FavoriteHorizontalAdapter(private var items: List<FavoriteEntity>, private val onItemClick: (FavoriteEntity) -> Unit) :
    RecyclerView.Adapter<FavoriteHorizontalAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: FavoriteHorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick.invoke(items[adapterPosition])
            }
        }
        fun bind(item: FavoriteEntity) {
            binding.name.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(FavoriteHorizontalItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateFavorites(newFavs: List<FavoriteEntity>) {
        items = newFavs
        notifyDataSetChanged() // Notifie le RecyclerView que les données ont changé et qu'il doit se rafraîchir
    }
}
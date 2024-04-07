package com.leperlier.quinquis.lentz.imdb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.databinding.SerieHorizontalItemBinding
import com.leperlier.quinquis.lentz.imdb.data.Category

class SerieHorizontalAdapter(private var items: List<Category>, private val onItemClick: (Category) -> Unit) :
    RecyclerView.Adapter<SerieHorizontalAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SerieHorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick.invoke(items[adapterPosition])
            }
        }
        fun bind(item: Category) {
            binding.name.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SerieHorizontalItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateCategories(newCategories: List<Category>) {
        items = newCategories
        notifyDataSetChanged() // Notifie le RecyclerView que les données ont changé et qu'il doit se rafraîchir
    }
}
package com.leperlier.quinquis.lentz.imdb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.databinding.AuthorHorizontalItemBinding
import com.leperlier.quinquis.lentz.imdb.data.Authors

class AuthorsHorizontalAdapter(private var items: List<Authors>, private val onItemClick: (Authors) -> Unit) :
    RecyclerView.Adapter<AuthorsHorizontalAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: AuthorHorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick.invoke(items[adapterPosition])
            }
        }

        fun bind(item: Authors) {
            binding.authorName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(AuthorHorizontalItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = items.size

    fun updateAuthors(newAuthors: List<Authors>) {
        items = newAuthors
        notifyDataSetChanged()
    }
}
package com.leperlier.quinquis.lentz.imdb.ui.providers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.ProviderItemBinding
import com.leperlier.quinquis.lentz.imdb.data.Provider

class ProvidersAdapter(private val providers: List<Provider>) : RecyclerView.Adapter<ProvidersAdapter.ProviderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val binding = ProviderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProviderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val provider = providers[position]
        holder.binding.providerName.text = provider.name // Utilisez providerName au lieu de providerLogo
    }

    override fun getItemCount(): Int = providers.size

    class ProviderViewHolder(val binding: ProviderItemBinding) : RecyclerView.ViewHolder(binding.root)
}

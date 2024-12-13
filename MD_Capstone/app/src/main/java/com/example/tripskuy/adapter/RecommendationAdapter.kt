package com.example.tripskuy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tripskuy.data.remote.response.PlaceRecommendation
import com.example.tripskuy.databinding.ItemCategoryBinding

class RecommendationAdapter(
    private var items: List<PlaceRecommendation>, // Bisa di-update
    private val onItemClick: (PlaceRecommendation) -> Unit
) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceRecommendation, onItemClick: (PlaceRecommendation) -> Unit) {
            binding.tvPlaceName.text = item.namePlace
            binding.tvPlaceTicket.text = item.ticketPlace.toString()
            binding.tvPlaceDescription.text = item.descPlace
            binding.tvPlaceRating.text = "Rating: ${item.ratingPlace}"

            Glide.with(binding.root.context)
                .load(item.urlImage)
                .into(binding.imgPlace)

            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<PlaceRecommendation>) {
        items = newItems
        notifyDataSetChanged()
    }
}

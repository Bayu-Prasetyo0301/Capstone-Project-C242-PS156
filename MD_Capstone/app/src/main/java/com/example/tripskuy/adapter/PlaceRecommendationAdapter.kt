package com.example.tripskuy.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tripskuy.R
import com.example.tripskuy.data.remote.response.PlaceRecommendation
import com.example.tripskuy.databinding.ItemPlaceBinding

class PlaceRecommendationAdapter(
    private val placeRecommendations: Array<out PlaceRecommendation>,
    private val onPlaceClicked: (PlaceRecommendation) -> Unit
) : RecyclerView.Adapter<PlaceRecommendationAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bindPlace(placeRecommendations[position])
    }

    override fun getItemCount(): Int = placeRecommendations.size

    inner class PlaceViewHolder(private val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindPlace(place: PlaceRecommendation) {
            binding.tvPlaceName.text = place.namePlace
            Log.d("PlaceRecommendationAdapter", "Place: ${place.namePlace}")
            binding.tvPlaceTicket.text = place.ticketPlace.toString()
            binding.tvPlaceDescription.text = place.descPlace
            binding.tvPlaceRating.text = "Rating: ${place.ratingPlace}"

            Glide.with(binding.imgPlace.context)
                .load(place.urlImage)
                .into(binding.imgPlace)

            binding.root.setOnClickListener {
                onPlaceClicked(place)
            }
        }
    }
}

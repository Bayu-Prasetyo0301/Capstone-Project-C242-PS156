package com.example.tripskuy.data.local.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tripskuy.data.local.db.PlaceEntity

import com.example.tripskuy.databinding.ItemPlaceBinding

class PlaceAdapter : ListAdapter<PlaceEntity, PlaceAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlaceEntity>() {
            override fun areItemsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(private val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceEntity) {
            binding.tvPlaceName.text = place.name
            binding.tvPlaceDescription.text = place.description
            binding.tvPlaceTicket.text = "Ticket: ${place.ticketPrice}"

            Glide.with(binding.imgPlace.context)
                .load(place.imageUrl)
                .into(binding.imgPlace)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

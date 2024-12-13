package com.example.tripskuy.data.local.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tripskuy.data.local.db.HotelEntity
import com.example.tripskuy.databinding.ItemHotelBinding

class HotelAdapter : ListAdapter<HotelEntity, HotelAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HotelEntity>() {
            override fun areItemsTheSame(oldItem: HotelEntity, newItem: HotelEntity): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: HotelEntity, newItem: HotelEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(private val binding: ItemHotelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: HotelEntity) {
            binding.tvHotelName.text = hotel.name
            binding.tvHotelFeatures.text = hotel.features
            binding.tvHotelPrice.text = "Price: ${hotel.price}"

            Glide.with(binding.imgHotel.context)
                .load(hotel.imageUrl)
                .into(binding.imgHotel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

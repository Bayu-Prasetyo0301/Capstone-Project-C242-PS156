package com.example.tripskuy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tripskuy.data.remote.response.HotelRecommendation
import com.example.tripskuy.databinding.ItemHotelBinding
import com.example.tripskuy.ui.detail.DetailActivity

class HotelRecommendationAdapter(
    private val context: Context,
    private val items: Array<out HotelRecommendation>,
    private val onItemClick: (HotelRecommendation) -> Unit
) : RecyclerView.Adapter<HotelRecommendationAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemHotelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HotelRecommendation, onItemClick: (HotelRecommendation) -> Unit) {
            binding.tvHotelName.text = item.nameHotel
            binding.tvHotelPrice.text = "Price: ${item.price}"
            binding.tvHotelCategory.text = item.categoryHotel
            binding.tvHotelFeatures.text = item.hotelFeatures
            binding.tvHotelRating.text = "Rating: ${item.starRating}"

            Glide.with(binding.root.context)
                .load(item.urlImage)
                .into(binding.imgHotel)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position]) { hotel ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("HOTEL_DATA", hotel)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}

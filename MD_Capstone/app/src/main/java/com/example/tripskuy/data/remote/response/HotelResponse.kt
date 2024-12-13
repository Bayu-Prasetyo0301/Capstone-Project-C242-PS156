package com.example.tripskuy.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HotelResponse(
	@SerializedName("recommendations")
	val recommendations: List<HotelRecommendation>
)
@Parcelize
data class HotelRecommendation(
	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("star_rating")
	val starRating: Float,

	@field:SerializedName("hotel_features")
	val hotelFeatures: String,

	@field:SerializedName("coordinates")
	val coordinates: String,

	@field:SerializedName("name_hotel")
	val nameHotel: String,

	@field:SerializedName("category_hotel")
	val categoryHotel: String,

	@field:SerializedName("url_image")
	val urlImage: String
) : Parcelable


package com.example.tripskuy.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PlaceResponse(
	@SerializedName("predicted_rating")
	val predictedRating: Float,

	@SerializedName("recommendations")
	val recommendations: List<PlaceRecommendation>
)

@Parcelize
data class PlaceRecommendation(
	@SerializedName("desc_place")
	val descPlace: String,

	@SerializedName("coordinate")
	val coordinate: Coordinate,

	@SerializedName("ticket_place")
	val ticketPlace: Int,

	@SerializedName("name_place")
	val namePlace: String,
	@SerializedName("rating_place")
	val ratingPlace: Float,

	@SerializedName("url_image")
	val urlImage: String
) : Parcelable

@Parcelize
data class Coordinate(
	@SerializedName("lat")
	val lat: Double,

	@SerializedName("lng")
	val lon: Double
) : Parcelable

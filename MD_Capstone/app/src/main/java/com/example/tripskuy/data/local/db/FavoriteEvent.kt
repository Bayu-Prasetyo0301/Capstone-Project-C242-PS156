package com.example.tripskuy.data.local.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "places")
@Parcelize
data class PlaceEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val ticketPrice: String,
    val imageUrl: String
) : Parcelable


@Entity(tableName = "hotels")
@Parcelize
data class HotelEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val features: String,
    val price: String,
    val imageUrl: String
) : Parcelable

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val imageUrl: String
)



package com.example.tripskuy.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(entities = [PlaceEntity::class, HotelEntity::class, FavoriteEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun hotelDao(): HotelDao
    abstract fun favoriteDao(): FavoriteDao
}


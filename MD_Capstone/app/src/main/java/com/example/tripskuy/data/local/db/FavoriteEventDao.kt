package com.example.tripskuy.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlaceEntity)

    @Query("SELECT * FROM places")
    fun getAllPlaces(): LiveData<List<PlaceEntity>>

    @Query("DELETE FROM places WHERE id = :id")
    suspend fun deletePlaceById(id: String)

}

@Dao
interface HotelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotel(hotel: HotelEntity)

    @Query("SELECT * FROM hotels")
    fun getAllHotels(): LiveData<List<HotelEntity>>

    @Query("DELETE FROM hotels WHERE id = :id")
    suspend fun deleteHotelById(id: String)

}
@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<FavoriteEntity>>

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun removeFavorite(id: String)
}



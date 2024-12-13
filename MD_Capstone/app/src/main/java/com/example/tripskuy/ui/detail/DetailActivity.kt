package com.example.tripskuy.ui.detail

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.example.tripskuy.R
import com.example.tripskuy.data.remote.response.PlaceRecommendation
import com.example.tripskuy.databinding.ActivityDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tripskuy.data.local.db.AppDatabase
import com.example.tripskuy.data.local.db.HotelEntity
import com.example.tripskuy.data.local.db.PlaceEntity
import com.example.tripskuy.data.remote.response.HotelRecommendation
import kotlinx.coroutines.launch

@Parcelize

class DetailActivity : AppCompatActivity(), OnMapReadyCallback, Parcelable {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var maps: GoogleMap
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_database"
        )
            .build()

        val placeData = intent.getParcelableExtra<PlaceRecommendation>("PLACE_DATA")
        if (placeData != null) {
            displayPlaceDetails(placeData)
        }

        val hotelData = intent.getParcelableExtra<HotelRecommendation>("HOTEL_DATA")
        if (hotelData != null) {
            displayHotelDetails(hotelData)
        }

        if (placeData != null) {
            displayPlaceDetails(placeData)
            setupFavoriteButton()
        }

        if (hotelData != null) {
            displayHotelDetails(hotelData)
            setupFavoriteButton()
        }


        val backButton = binding.imageBack
        backButton.setOnClickListener {
            onBackPressed()
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        val mapMenuButton: ImageView = binding.mapMenuButton
        mapMenuButton.setOnClickListener {
            showMapTypeMenu(mapMenuButton)
        }
        
    }
    private fun setupFavoriteButton() {
        val placeData = intent.getParcelableExtra<PlaceRecommendation>("PLACE_DATA")
        val hotelData = intent.getParcelableExtra<HotelRecommendation>("HOTEL_DATA")

        lifecycleScope.launch {
            val itemId = placeData?.let { "${it.namePlace}_${it.urlImage}".hashCode().toString() }
                ?: hotelData?.let { "${it.nameHotel}_${it.urlImage}".hashCode().toString() }

            if (itemId != null) {
                val favoriteItems = mutableListOf<Any>()

                if (placeData != null) {
                    database.placeDao().getAllPlaces().observe(this@DetailActivity) { places ->
                        val isFavorite = places.any { it.id == itemId }
                        updateFavoriteIcon(isFavorite)

                        binding.iconFavorite.setOnClickListener {
                            lifecycleScope.launch {
                                if (isFavorite) {
                                    database.placeDao().deletePlaceById(itemId)
                                    showToast("Removed from favorites")
                                } else {
                                    savePlaceToFavorites(placeData)
                                    showToast("Added to favorites")
                                }
                                updateFavoriteIcon(!isFavorite)  // Toggle the icon
                            }
                        }
                    }
                } else if (hotelData != null) {
                    database.hotelDao().getAllHotels().observe(this@DetailActivity) { hotels ->
                        val isFavorite = hotels.any { it.id == itemId }
                        updateFavoriteIcon(isFavorite)

                        binding.iconFavorite.setOnClickListener {
                            lifecycleScope.launch {
                                if (isFavorite) {
                                    database.hotelDao().deleteHotelById(itemId)
                                    showToast("Removed from favorites")
                                } else {
                                    saveHotelToFavorites(hotelData)
                                    showToast("Added to favorites")
                                }
                                updateFavoriteIcon(!isFavorite)  // Toggle the icon
                            }
                        }
                    }
                }
            }
        }
    }


    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.iconFavorite.setImageResource(R.drawable.ic_favorite)

        val color = if (isFavorite) {
            ContextCompat.getColor(this, R.color.blue1)
        } else {
            ContextCompat.getColor(this, R.color.black)
        }

        binding.iconFavorite.setColorFilter(color)
    }



    private fun savePlaceToFavorites(place: PlaceRecommendation) {
        val placeEntity = PlaceEntity(
            id = "${place.namePlace}_${place.urlImage}".hashCode().toString(),
            name = place.namePlace,
            description = place.descPlace,
            ticketPrice = place.ticketPlace.toString(),
            imageUrl = place.urlImage
        )

        lifecycleScope.launch {
            try {
                database.placeDao().insertPlace(placeEntity)
                runOnUiThread {
                    showToast("Place added to favorites!")
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun saveHotelToFavorites(hotel: HotelRecommendation) {
        val hotelEntity = HotelEntity(
            id = "${hotel.nameHotel}_${hotel.urlImage}".hashCode().toString(),
            name = hotel.nameHotel,
            features = hotel.hotelFeatures,
            price = hotel.price.toString(),
            imageUrl = hotel.urlImage
        )

        lifecycleScope.launch {
            try {
                database.hotelDao().insertHotel(hotelEntity)
                runOnUiThread {
                    showToast("Hotel added to favorites!")
                }
            } catch (e: Exception) {
                Log.e("SaveHotelToFavorites", "Error saving hotel to database", e)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun displayPlaceDetails(place: PlaceRecommendation) {
        binding.title.text = place.namePlace
        binding.ticketPrice.text = place.ticketPlace.toString()
        binding.description.text = place.descPlace

        Glide.with(this)
            .load(place.urlImage)
            .into(binding.imageHeader)
    }

    private fun displayHotelDetails(hotel: HotelRecommendation) {
        binding.title.text = hotel.nameHotel
        binding.ticketPrice.text = hotel.price.toString()
        binding.description.text = hotel.hotelFeatures

        Glide.with(this)
            .load(hotel.urlImage)
            .into(binding.imageHeader)
    }


    override fun onMapReady(map: GoogleMap) {
        maps = map
        setupMap()

        val placeData = intent.getParcelableExtra<PlaceRecommendation>("PLACE_DATA")
        if (placeData != null) {
            val latLng = LatLng(placeData.coordinate.lat, placeData.coordinate.lon)
            val marker = maps.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(placeData.namePlace)
                    .icon(vectorToBitmap(R.drawable.ic_location, Color.RED))
            )
            marker?.showInfoWindow()
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }

        val hotelData = intent.getParcelableExtra<HotelRecommendation>("HOTEL_DATA")
        if (hotelData != null) {
            val coordinates = hotelData.coordinates.split(",")
            val latitude = coordinates[0].trim().toDouble()
            val longitude = coordinates[1].trim().toDouble()

            val latLng = LatLng(latitude, longitude)

            val marker = maps.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(hotelData.nameHotel)
                    .icon(vectorToBitmap(R.drawable.ic_location, Color.BLUE))
            )
            marker?.showInfoWindow()
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }

    private fun setupMap() {
        maps.uiSettings.isZoomControlsEnabled = true
        maps.uiSettings.isMapToolbarEnabled = true
    }

    private fun vectorToBitmap(resourceId: Int, color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, resourceId, null)
            ?: return BitmapDescriptorFactory.defaultMarker()
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun showMapTypeMenu(anchorView: ImageView) {
        val popupMenu = PopupMenu(this, anchorView)
        popupMenu.menuInflater.inflate(R.menu.map_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.normal_type -> {
                    maps.mapType = GoogleMap.MAP_TYPE_NORMAL
                    true
                }
                R.id.satellite_type -> {
                    maps.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    true
                }
                R.id.terrain_type -> {
                    maps.mapType = GoogleMap.MAP_TYPE_TERRAIN
                    true
                }
                R.id.hybrid_type -> {
                    maps.mapType = GoogleMap.MAP_TYPE_HYBRID
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}

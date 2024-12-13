package com.example.tripskuy.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.tripskuy.data.local.adapter.HotelAdapter
import com.example.tripskuy.data.local.adapter.PlaceAdapter
import com.example.tripskuy.data.local.db.AppDatabase
import com.example.tripskuy.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var database: AppDatabase
    private val placeAdapter = PlaceAdapter()
    private val hotelAdapter = HotelAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "app_database"
        ).build()

        binding.rvHotel.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlace.layoutManager = LinearLayoutManager(requireContext())

        binding.rvHotel.adapter = hotelAdapter
        binding.rvPlace.adapter = placeAdapter

        // Observe place data
        database.placeDao().getAllPlaces().observe(viewLifecycleOwner) { places ->
            placeAdapter.submitList(places)
        }

        // Observe hotel data
        database.hotelDao().getAllHotels().observe(viewLifecycleOwner) { hotels ->
            hotelAdapter.submitList(hotels)
        }
    }
}

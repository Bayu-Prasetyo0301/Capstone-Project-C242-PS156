package com.example.tripskuy.ui.rekomendasi

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripskuy.adapter.HotelRecommendationAdapter
import com.example.tripskuy.data.remote.response.HotelRecommendation
import com.example.tripskuy.databinding.FragmentHotelRecommendationBinding
import com.example.tripskuy.ui.detail.DetailActivity

class HotelRecommendationFragment : Fragment() {

    private var _binding: FragmentHotelRecommendationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelRecommendationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil data dari Bundle
        val hotelRecommendations = arguments?.getParcelableArray("hotelRecommendations")
            ?.filterIsInstance<HotelRecommendation>()

        if (!hotelRecommendations.isNullOrEmpty()) {
            Log.d("HotelRecommendationFragment", "Received hotel recommendations: $hotelRecommendations")

            // Membuat adapter dengan intent ke DetailCategory
            val recommendationHotelAdapter = HotelRecommendationAdapter(requireContext(), hotelRecommendations.toTypedArray()) { hotel ->
                Log.d("HotelRecommendationFragment", "Navigating to DetailCategory with data: $hotel")

                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("PLACE_DATA", hotel)
                }
                startActivity(intent)
            }

            binding.rvHotel.adapter = recommendationHotelAdapter

            // Mengatur RecyclerView
            binding.rvHotel.apply {
                layoutManager = GridLayoutManager(requireContext(), 2) // Menggunakan GridLayoutManager dengan 2 kolom
                adapter = recommendationHotelAdapter
                addItemDecoration(SpaceItemDecoration(16)) // Menambahkan jarak antar item
            }
        } else {
            Log.e("HotelRecommendationFragment", "No hotel recommendations received or list is empty")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            val spanCount = 2 // Jumlah kolom
            val column = position % spanCount // Kolom item

            outRect.top = space
            outRect.bottom = space
            outRect.left = space - column * space / spanCount
            outRect.right = (column + 1) * space / spanCount
        }
    }
}

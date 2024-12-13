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
import com.example.tripskuy.adapter.PlaceRecommendationAdapter
import com.example.tripskuy.data.remote.response.PlaceRecommendation
import com.example.tripskuy.databinding.FragmentPlaceRecommendationBinding
import com.example.tripskuy.ui.detail.DetailActivity

class PlaceRecommendationFragment : Fragment() {

    private var _binding: FragmentPlaceRecommendationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceRecommendationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil data dari Bundle
        val placeRecommendations = arguments?.getParcelableArray("placeRecommendations")
            ?.filterIsInstance<PlaceRecommendation>()

        if (!placeRecommendations.isNullOrEmpty()) {
            Log.d("PlaceRecommendationFragment", "Received place recommendations: $placeRecommendations")

            // Membuat adapter dengan intent ke DetailCategory
            val adapter = PlaceRecommendationAdapter(placeRecommendations.toTypedArray()) { place ->
                Log.d("PlaceRecommendationFragment", "Place clicked: ${place.namePlace}")

                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("PLACE_DATA", place)
                }
                startActivity(intent)
            }

            // Mengatur RecyclerView
            binding.rvPlace.apply {
                layoutManager = GridLayoutManager(requireContext(), 2) // Menggunakan GridLayoutManager dengan 2 kolom
                this.adapter = adapter
                addItemDecoration(SpaceItemDecoration(16)) // Menambahkan jarak antar item
            }
        } else {
            Log.e("PlaceRecommendationFragment", "No place recommendations received or list is empty")
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

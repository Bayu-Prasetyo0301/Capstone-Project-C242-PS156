package com.example.tripskuy.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tripskuy.R
import com.example.tripskuy.adapter.RecommendationAdapter
import com.example.tripskuy.data.remote.response.PlaceRecommendation
import com.example.tripskuy.data.remote.response.PlaceResponse
import com.example.tripskuy.data.remote.retrofit.ApiConfig
import com.example.tripskuy.databinding.FragmentHomeBinding
import com.example.tripskuy.ui.detail.DetailActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class HomeFragment : Fragment() {
    private lateinit var recommendationAdapter: RecommendationAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.progressBar

        setupRecommendationRecyclerView()
        setupCategoryNavigation()
        loadRecommendations()

        binding.destinasiButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_destinasiInputFragment)
        }

        binding.hotelButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_hotelInputFragment)
        }
    }

    private fun setupRecommendationRecyclerView() {
        recommendationAdapter = RecommendationAdapter(emptyList()) { place ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            findNavController().navigate(action)
        }

        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendationAdapter
        }
    }

    private fun loadRecommendations() {
        progressBar.visibility = View.VISIBLE

        val defaultCities = listOf("Bandung", "Jakarta", "Yogyakarta")
        val defaultCategories = listOf("Bahari", "Budaya", "Taman Hiburan")

        val allRecommendations = mutableListOf<PlaceRecommendation>()
        var completedRequestsCount = 0
        val totalRequests = defaultCities.size * defaultCategories.size

        defaultCities.forEach { city ->
            defaultCategories.forEach { category ->
                val ticketPrice = when (category) {
                    "Bahari" -> 15000
                    "Budaya" -> 12000
                    "Taman Hiburan" -> 12000
                    else -> 10000
                }

                try {
                    val encodedCategory = URLEncoder.encode(category, "UTF-8").replace("+", "%20")
                    val encodedCity = URLEncoder.encode(city, "UTF-8").replace("+", "%20")

                    Log.d("HomeFragment", "Posting API with: category=$encodedCategory, city=$encodedCity, ticketPrice=$ticketPrice")

                    ApiConfig.getApiServicePlace()
                        .getDestinations(encodedCategory, encodedCity, ticketPrice)
                        .enqueue(object : Callback<PlaceResponse> {
                            override fun onResponse(
                                call: Call<PlaceResponse>,
                                response: Response<PlaceResponse>
                            ) {
                                if (response.isSuccessful) {
                                    Log.d("HomeFragment", "Response success: $response")
                                    response.body()?.recommendations?.let {
                                        allRecommendations.addAll(it)
                                    }
                                } else {
                                    Log.e("HomeFragment", "Response error: ${response.code()}")
                                }

                                completedRequestsCount++
                                if (completedRequestsCount == totalRequests) {
                                    handleRecommendationLoad(allRecommendations)
                                }
                            }

                            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                                Log.e("HomeFragment", "API call failed: ${t.message}")
                                completedRequestsCount++
                                if (completedRequestsCount == totalRequests) {
                                    handleRecommendationLoad(allRecommendations)
                                }
                            }
                        })
                } catch (e: UnsupportedEncodingException) {
                    Log.e("HomeFragment", "Error encoding URL: ${e.message}")
                }
            }
        }
    }

    private fun handleRecommendationLoad(recommendations: List<PlaceRecommendation>) {
        progressBar.visibility = View.GONE

        if (recommendations.isNotEmpty()) {
            val sortedRecommendations = recommendations
                .sortedByDescending { it.ratingPlace }
                .take(10)

            Log.d("HomeFragment", "Sorted recommendations: $sortedRecommendations")

            recommendationAdapter = RecommendationAdapter(sortedRecommendations) { place ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                findNavController().navigate(action)
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("PLACE_DATA", place)
                }
                startActivity(intent)
            }

            binding.rvCategory.adapter = recommendationAdapter
        } else {
            Log.e("HomeFragment", "No recommendations available")
            Toast.makeText(requireContext(), "Tidak ada data rekomendasi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCategoryNavigation() {
        val categoryMap = mapOf(
            binding.bahariButton.id to "Bahari",
            binding.mallButton.id to "Pusat Perbelanjaan",
            binding.budayaButton.id to "Budaya",
            binding.cagarAlamButton.id to "Cagar Alam",
            binding.tamanHiburanButton.id to "Taman Hiburan",
            binding.tempatIbadahButton.id to "Tempat Ibadah"
        )

        categoryMap.forEach { (viewId, category) ->
            requireView().findViewById<View>(viewId).setOnClickListener {
                progressBar.visibility = View.VISIBLE
                lifecycleScope.launch {
                    loadCategoryData(category)
                }
            }
        }
    }

    private fun loadCategoryData(category: String) {
        val validCities = listOf(
            "Bandung", "Jakarta Utara", "Surabaya", "Jakarta Timur", "Jakarta Selatan",
            "Jakarta Barat", "Jakarta Pusat", "Yogyakarta", "Kepulauan Seribu", "Semarang"
        )

        val ticketPriceMap = mapOf(
            "Bahari" to 10000,
            "Pusat Perbelanjaan" to 10000,
            "Budaya" to 10000,
            "Cagar Alam" to 10000,
            "Taman Hiburan" to 12000,
            "Tempat Ibadah" to 0
        )

        val ticketPrice = ticketPriceMap[category] ?: 0
        val allRecommendations = mutableListOf<PlaceRecommendation>()
        var completedCitiesCount = 0

        validCities.forEach { city ->
            try {
                val encodedCategory = URLEncoder.encode(category, "UTF-8").replace("+", "%20")
                val encodedCity = URLEncoder.encode(city, "UTF-8").replace("+", "%20")
                Log.d("API Input", "Category: $encodedCategory, City: $encodedCity, TicketPrice: $ticketPrice")

                ApiConfig.getApiServicePlace()
                    .getDestinations(encodedCategory, encodedCity, ticketPrice)
                    .enqueue(object : Callback<PlaceResponse> {
                        override fun onResponse(
                            call: Call<PlaceResponse>,
                            response: Response<PlaceResponse>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.recommendations?.let {
                                    allRecommendations.addAll(it)
                                }
                            } else {
                                Log.e("HomeFragment", "Error in response for $city: ${response.code()}")
                            }
                            checkAllRequestsCompleted()
                        }

                        override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                            Log.e("HomeFragment", "API call failed for $city: ${t.message}")
                            checkAllRequestsCompleted()
                        }

                        private fun checkAllRequestsCompleted() {
                            completedCitiesCount++
                            if (completedCitiesCount == validCities.size) {
                                progressBar.visibility = View.GONE
                                if (allRecommendations.isNotEmpty()) {
                                    navigateToCategoryFragment(category, allRecommendations)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Tidak ada data yang dapat ditampilkan.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    })
            } catch (e: UnsupportedEncodingException) {
                Log.e("HomeFragment", "Error encoding URL: ${e.message}")
                completedCitiesCount++
            }
        }
    }


    private fun navigateToCategoryFragment(category: String, recommendations: List<PlaceRecommendation>) {
        val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(
            category,
            recommendations.toTypedArray()
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

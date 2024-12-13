package com.example.tripskuy.ui.category

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tripskuy.data.remote.response.PlaceRecommendation
import com.example.tripskuy.data.remote.response.PlaceResponse
import com.example.tripskuy.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryViewModel : ViewModel() {

    private val _recommendations = MutableStateFlow<List<PlaceRecommendation>>(emptyList())
    val recommendations: StateFlow<List<PlaceRecommendation>> = _recommendations

    fun fetchDestinations(category: String, city: String, ticketPrice: Int) {
        // Lakukan panggilan API dan update state
        val apiService = ApiConfig.getApiServicePlace()
        apiService.getDestinations(category, city, ticketPrice)
            .enqueue(object : Callback<PlaceResponse> {
                override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                    if (response.isSuccessful) {
                        val recommendations = response.body()?.recommendations ?: emptyList()
                        // Log data yang diterima
                        Log.d("CategoryViewModel", "Data received: ${recommendations.size} items")
                        _recommendations.value = recommendations
                    } else {
                        Log.e("CategoryViewModel", "API Response not successful: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                    Log.e("CategoryViewModel", "API call failed: ${t.message}")
                }
            })
    }
}


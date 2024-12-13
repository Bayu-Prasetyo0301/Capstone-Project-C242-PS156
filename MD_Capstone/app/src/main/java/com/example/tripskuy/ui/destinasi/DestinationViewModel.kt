package com.example.tripskuy.ui.destinasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tripskuy.data.remote.response.PlaceRecommendation

class DestinationViewModel : ViewModel() {

    private val _placeRecommendations = MutableLiveData<List<PlaceRecommendation>>()
    val placeRecommendations: LiveData<List<PlaceRecommendation>> get() = _placeRecommendations

    fun setPlaceRecommendations(recommendations: List<PlaceRecommendation>) {
        _placeRecommendations.value = recommendations
    }
}

package com.example.tripskuy.ui.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tripskuy.data.remote.response.HotelResponse

class HotelViewModel : ViewModel() {

    private val _hotelResponse = MutableLiveData<HotelResponse>()
    val hotelResponse: LiveData<HotelResponse> get() = _hotelResponse

    fun setHotelResponse(response: HotelResponse) {
        _hotelResponse.value = response
    }
}

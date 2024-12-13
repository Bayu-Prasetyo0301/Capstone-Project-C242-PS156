package com.example.tripskuy.data.remote.retrofit

import com.example.tripskuy.data.remote.response.HotelResponse
import com.example.tripskuy.data.remote.response.PlaceResponse
import com.example.tripskuy.data.request.DestinationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.POST

interface   ApiServices {

    @GET("recommendations/")
    fun getDestinations(
        @Query("category") category: String,
        @Query("city") city: String,
        @Query("ticket_price") ticketPrice: Int
    ): Call<PlaceResponse>

    @POST("recommendations/")
    fun postDestination(@Body destination: DestinationRequest): Call<PlaceResponse>

    @POST("recommendations/")
    fun postHotel(@Body hotel: Map<String, String>): Call<HotelResponse>
}



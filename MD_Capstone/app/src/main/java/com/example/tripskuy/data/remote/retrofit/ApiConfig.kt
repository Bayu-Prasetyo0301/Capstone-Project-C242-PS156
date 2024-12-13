package com.example.tripskuy.data.remote.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiConfig {
    private const val BASE_URL_PLACE = "https://rekomendasi-tempat-625541638853.asia-southeast2.run.app/"
    private const val BASE_URL_HOTEL = "https://rekomendasi-hotel-625541638853.asia-southeast2.run.app/"

    private fun getRetrofit(baseUrl: String): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiServicePlace(): ApiServices {
        return getRetrofit(BASE_URL_PLACE).create(ApiServices::class.java)
    }

    fun getApiServiceHotel(): ApiServices {
        return getRetrofit(BASE_URL_HOTEL).create(ApiServices::class.java)
    }
}
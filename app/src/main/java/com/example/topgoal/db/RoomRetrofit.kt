package com.example.topgoal.db

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RoomRetrofit {
    fun getClient(baseUrl: String): Retrofit? = Retrofit.Builder()
            .baseUrl(baseUrl).client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

object InformationService {
    private const val retrofitUrl = "http://3.38.34.47:8080/"
    val client = RoomRetrofit().getClient(retrofitUrl)?.create(RoomInterface::class.java)
}
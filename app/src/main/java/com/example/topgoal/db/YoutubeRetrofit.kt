package com.example.topgoal.db

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YoutubeRetrofit {
    fun getClient(baseUrl: String): Retrofit? = Retrofit.Builder()
        .baseUrl(baseUrl).client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

object PlayListService {
    private const val PlayListUrl = "https://www.googleapis.com/youtube/v3/"

    val client = YoutubeRetrofit().getClient(PlayListUrl)?.create(PlayListInterface::class.java)
}
package com.example.topgoal.db

import com.example.topgoal.db.PlayList
import com.example.topgoal.db.PlayListItems
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayListInterface {

    @GET ("playlists")
    suspend fun getPlayList(
        @Query("key")key: String,
        @Query("part")part: String,
        @Query("channelId") channelId:String
    ): PlayList

    @GET("playlistItems")
    suspend fun getPlayListItems(
        @Query("key")key: String,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 50
    ):PlayListItems

}
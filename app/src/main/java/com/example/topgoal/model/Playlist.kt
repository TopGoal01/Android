package com.example.topgoal.model

data class Playlist(
        var id: String,
        var name: String? = null,
        var count: Int = 0,
        var videoList: MutableList<Video>? = null
)

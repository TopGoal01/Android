package com.example.topgoal.model

data class SocketChat(
        val message: String,
        val roomId: String?,
        val userID: String,
        val url: String
)

package com.example.topgoal.model

import java.time.LocalDateTime

data class SocketChat(
        val message: String,
        val roomId: String?,
        val userID: String,
        val picUrl: String,
        val localDateTime: LocalDateTime
)

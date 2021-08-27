package com.example.topgoal.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class SocketChat(
        @SerializedName("message") val message: String,
        @SerializedName("roomId") val roomId: String?,
        @SerializedName("userID") val userID: String,
        @SerializedName("picUrl") val picUrl: String,
        @SerializedName("localDateTime") val localDateTime: String,
        @SerializedName("userName") val userName: String
)

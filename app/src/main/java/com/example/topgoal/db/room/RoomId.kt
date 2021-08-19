package com.example.topgoal.db.room

data class RoomId(
    val admin: Admin,
    val createdDate: String,
    val roomId: String,
    val roomName: String,
    val userCount: Int
)
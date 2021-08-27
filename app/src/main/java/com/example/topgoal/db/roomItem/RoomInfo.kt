package com.example.topgoal.db.roomItem

data class RoomInfo(
    val admin: User,
    val createdDate: String,
    val roomId: String,
    val userCount: Int
)
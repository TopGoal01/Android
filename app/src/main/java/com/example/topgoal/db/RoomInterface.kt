package com.example.topgoal.db

import com.example.topgoal.db.room.RoomInfo
import com.example.topgoal.db.room.RoomList
import com.example.topgoal.db.room.User
import retrofit2.http.*

interface RoomInterface {

    @GET("room/{roomId}")
    suspend fun getRoom(
            @Path("roomId") roomId: String
    ): RoomInfo

    @DELETE("room/{roomId}")
    suspend fun deleteRoom(
            @Path("roomId") roomId: String
    )

    @POST("room/{roomId}")
    suspend fun postRoom(
            @Path("roomId") roomId: String,
            @Query("user") user: String
    ): RoomInfo

    @GET("room/users")
    suspend fun getRoomUser(
            @Query("roomId") roomId: String
    ): RoomList

    @POST("user/{roomId}")
    suspend fun postEnter(
            @Path("roomId") roomId: String,
            @Query("userId") userId: String
    ): RoomInfo

    @DELETE("user/{roomId}")
    suspend fun leaveRoom(
            @Path("roomId") roomId: String,
            @Query("userId") userId: String
    )

    @GET("user/rooms")
    suspend fun getUserRooms(
            @Query("userId") userId: String
    ): RoomList

    @GET("user/{userId}")
    suspend fun getUserInfo(
            @Path("userId") userId: String
    ): User

    @POST("user/{userToken}")
    suspend fun postUserAuth(
            @Path("userToken") userToken: String
    ): User

}
package com.example.topgoal.db

import com.example.topgoal.db.roomItem.RoomInfo
import com.example.topgoal.db.roomItem.RoomList
import com.example.topgoal.db.roomItem.User
import retrofit2.Response
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

    @POST("room/{userID}")
    suspend fun postRoom(
            @Path("userID") userId: String,
    ): Response<RoomInfo>

    @GET("room/users")
    suspend fun getRoomUser(
            @Query("roomId") roomId: String
    ): Response<RoomList>

    @POST("user/{roomId}")
    suspend fun postEnter(
            @Path("roomId") roomId: String,
            @Query("userId") userId: String
    ): Response<RoomInfo>

    @DELETE("user/{roomId}")
    suspend fun deleteRoomUser(
            @Path("roomId") roomId: String,
            @Query("userId") userId: String
    )

    @GET("user/rooms")
    suspend fun getUsers(
            @Query("userId") userId: String
    ): Response<RoomList>

    @GET("user/{userId}")
    suspend fun getUserInfo(
            @Path("userId") userId: String
    ): Response<User>

    @POST("user/{userToken}")
    suspend fun postUserAuth(
            @Path("userToken") userToken: String
    ): Response<User>

}
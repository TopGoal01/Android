package com.example.topgoal.db

import com.example.topgoal.db.roomItem.RoomInfo
import com.example.topgoal.db.roomItem.RoomList
import com.example.topgoal.db.roomItem.User
import retrofit2.Response
import retrofit2.http.*

interface RoomInterface {

    //Room
    @GET("room/{roomId}")
    suspend fun getRoom(
            @Path("roomId") roomId: String
    ): RoomInfo

    @DELETE("room/{roomId}")
    suspend fun deleteRoom(
            @Path("roomId") roomId: String
    )

    @POST("room/{userToken}")
    suspend fun postRoom(
            @Path("userID") userId: String,
    ): Response<RoomInfo>

    //Room Member
    @GET("member/users")
    suspend fun getRoomUser(
            @Query("roomId") roomId: String
    ): Response<RoomList>

    @POST("member/{roomId}")
    suspend fun postEnter(
            @Path("roomId") roomId: String,
            @Query("userToken") userToken: String
    ): Response<RoomInfo>

    @DELETE("member/{roomId}")
    suspend fun deleteRoomUser(
            @Path("roomId") roomId: String,
            @Query("userToken") userToken: String
    )

    @GET("member/rooms")
    suspend fun getUsers(
            @Query("userToken") userToken: String
    ): Response<RoomList>

    //User
    @GET("user/{userToken}")
    suspend fun getUserInfo(
            @Path("userToken") userToken: String
    ): Response<User>

    @POST("user/{userToken}")
    suspend fun postUserAuth(
            @Path("userToken") userToken: String
    ): Response<User>

}
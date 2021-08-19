package com.example.topgoal.db

import com.example.topgoal.db.room.Room
import com.example.topgoal.db.room.RoomId
import com.example.topgoal.db.room.UserId
import retrofit2.http.*

interface RoomInterface {

    @GET("room/{roomId}")
    suspend fun getRoom(
        @Path("roomId")roomId: String
    ): RoomId

    @DELETE("room/{roomId}")
    suspend fun deleteRoom(
        @Path("roomId")roomId:String
    )

    @POST("room/{roomId}")
    suspend fun postRoom(
        @Path("roomId")roomId:String,
        @Query("user")user:String
    ) : RoomId

    @GET("room/users")
    suspend fun getRoomUser(
            @Query("roomId")roomId:String
    ): Room

    @POST("user/{roomId}")
    suspend fun postEnter(
            @Path("roomId")roomId:String,
            @Query("userId")userId:String
    ) :RoomId

    @DELETE("user/{roomId}")
    suspend fun leaveRoom(
            @Path("roomId")roomId:String,
            @Query("userId")userId:String
    )

    @GET("user/rooms")
    suspend fun getUserRooms(
            @Query("userId")userId:String
    ):Room

    @GET("user/{userId}")
    suspend fun getUserInfo(
            @Path("userId")userId:String
    ): UserId

    @POST("user/{userToken}")
    suspend fun postUserAuth(
            @Path("userToken")userToken:String
    ):UserId
}
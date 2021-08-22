package com.example.topgoal.db

import com.example.topgoal.db.roomItem.RoomInfo
import com.example.topgoal.db.roomItem.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class RoomRepository {

    companion object {
        lateinit var userId: String
        lateinit var userName: String
        lateinit var userPic: String

        lateinit var roomId: String
        lateinit var roomName: String
        lateinit var adminId: String

        fun setRoomNull() {
            roomId = ""
            roomName = ""
            adminId = ""
        }

        fun setRoom(currentRoom: RoomInfo) {
            adminId = currentRoom.admin.id
            roomName = currentRoom.roomName
            roomId = currentRoom.roomId
        }

        fun setCurrentUser(currentUser: User) {
            userId = currentUser.id
            userName = currentUser.name
            userPic = currentUser.userPic
        }

        fun deleteRoomOrUser() {
            CoroutineScope(Dispatchers.IO).launch {
                if (userId == adminId) deleteRoom()
                else deleteRoomUser()
            }
        }

        suspend fun deleteRoom() {
            val RetCo = CoroutineScope(Dispatchers.IO).launch {
                InformationService.client!!.deleteRoom(
                        roomId = roomId
                )
            }

            if (RetCo.isCompleted) {
                setRoomNull()
            }
        }

        suspend fun deleteRoomUser() {
            val RetCo = CoroutineScope(Dispatchers.IO).async {
                val response = InformationService.client!!.deleteRoomUser(
                        roomId = roomId,
                        userId = userId
                )
            }
            if (RetCo.isCompleted) {
                setRoomNull()
            }
        }

        suspend fun postRoom(): Boolean {
            val RetCo = CoroutineScope(Dispatchers.IO).async {
                val response: Response<RoomInfo> = InformationService.client!!.postRoom(
                        roomId = roomId,
                        user = userId
                )
                if (response.isSuccessful) {
                    setRoom(response.body()!!)
                    true
                } else {
                    false
                }
            }
            return RetCo.await()
        }

        suspend fun postEnter(curRoomId: String): Boolean {
            val RetCo = CoroutineScope(Dispatchers.IO).async {
                val response: Response<RoomInfo> = InformationService.client!!.postEnter(
                        roomId = curRoomId,
                        userId = userId
                )
                if (response.isSuccessful) {
                    setRoom(response.body()!!)
                    true
                } else {
                    false
                }
            }
            return RetCo.await()
        }

        suspend fun getUserInfo(searchUserId: String): User? {
            val RetCo = CoroutineScope(Dispatchers.IO).async {
                val response: Response<User> = InformationService.client!!.getUserInfo(
                        userId = searchUserId
                )
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            }
            return RetCo.await()
        }

        suspend fun postUserAuth(token: String): Boolean {
            val RetCo = CoroutineScope(Dispatchers.IO).async {
                val response: Response<User> = InformationService.client!!.postUserAuth(
                        userToken = token
                )

                if (response.isSuccessful) {
                    setCurrentUser(response.body()!!)
                    true
                } else {
                    false
                }
            }
            return RetCo.await()
        }
    }
}
package com.purple.data.rooms

import com.purple.core.database.dao.RoomDao
import com.purple.core.database.entity.RoomEntity
import com.purple.core.database.model.RoomWithMembers
import com.purple.core.database.model.asExternalModel
import com.purple.core.model.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: RoomDao,
    private val remoteRoomDataSource: RemoteRoomDataSource,
) : RoomRepository {
    override fun getRooms(): Flow<List<Room>> =
        roomDao.getRoomsWithMembers().map { it.map(RoomWithMembers::asExternalModel) }

    override suspend fun createRoom(roomName: String, userName: String, roomQuestion: String, roomPassword: String) {
        remoteRoomDataSource.createRoom(
            roomName,
            userName,
            roomQuestion,
            roomPassword,
        ).let {
            if (it.isSuccessful) {
                it.body()?.let { response ->
                    roomDao.insertRoom(
                        RoomEntity(
                            userRoomId = response.userRoomId,
                            roomId = response.roomId,
                            roomName = response.roomName,
                            recentVisitedTime = System.currentTimeMillis(),
                        ),
                    )
                }
            }
        }
    }

    override fun joinRoom(roomCode: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateRoomName(userRoomId: Long, roomName: String) {
        remoteRoomDataSource.updateRoomName(
            userRoomId,
            roomName,
        ).let {
            if (it.isSuccessful) {
                roomDao.updateRoomName(
                    userRoomId = userRoomId,
                    roomName = roomName,
                )
            }
        }
    }

    override suspend fun updateUserName(userRoomId: Long, userName: String) {
        remoteRoomDataSource.updateUserName(
            userRoomId,
            userName,
        ).let {
            if (it.isSuccessful) {
                roomDao.updateUserName(
                    userRoomId = userRoomId,
                    userName = userName,
                )
            }
        }
    }

    override suspend fun updatePassword(roomId: Long, passwordQuestion: String, password: String) {
        remoteRoomDataSource.updatePassword(
            roomId,
            password,
            passwordQuestion,
        ).let {
            if (it.isSuccessful) {
                roomDao.updatePassword(
                    roomId = roomId,
                    password = password,
                    question = passwordQuestion,
                )
            }
        }
    }

    override suspend fun leaveRoom(userRoomId: Long) {
        remoteRoomDataSource.exitRoom(
            userRoomId,
        ).let {
            if (it.isSuccessful) {
                roomDao.exitRoom(
                    userRoomId = userRoomId,
                )
            }
        }
    }

    override suspend fun deleteRoom(roomId: Long) {
        remoteRoomDataSource.deleteRoom(
            roomId,
        ).let {
            if (it.isSuccessful) {
                roomDao.deleteRoom(
                    roomId = roomId,
                )
            }
        }
    }
}

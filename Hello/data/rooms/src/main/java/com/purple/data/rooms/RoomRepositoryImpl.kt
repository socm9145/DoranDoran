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

    override fun getRoom(roomId: Long): Flow<Room> =
        roomDao.getRoom(roomId).map(RoomWithMembers::asExternalModel)

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

    override fun updateUserName(userName: String) {
        TODO("Not yet implemented")
    }

    override fun updateRoomName(roomName: String) {
        TODO("Not yet implemented")
    }

    override fun updatePassword(passwordQuestion: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun leaveRoom(roomId: Int) {
        TODO("Not yet implemented")
    }

    override fun deleteRoom(roomId: Int) {
        TODO("Not yet implemented")
    }
}

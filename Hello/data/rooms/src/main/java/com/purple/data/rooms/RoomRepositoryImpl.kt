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
            roomDao.insertRoom(
                RoomEntity(
                    userRoomId = it.userRoomId,
                    roomId = it.roomId,
                    roomName = it.roomName,
                    recentVisitedTime = System.currentTimeMillis(),
                )
            )
        }
//        roomDao.insertRoom(
//            RoomEntity(
//                roomId = 0,
//                userRoomId = 0,
//                roomName = roomName,
//                recentVisitedTime = System.currentTimeMillis(),
//            ),
//        )
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

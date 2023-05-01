package com.purple.data.rooms

import com.purple.core.database.dao.RoomDao
import com.purple.core.database.model.RoomWithMembers
import com.purple.core.database.model.asExternalModel
import com.purple.core.model.CommonOptions
import com.purple.core.model.PersonalOptions
import com.purple.core.model.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: RoomDao,
) : RoomRepository {
    override fun getRooms(): Flow<List<Room>> =
        roomDao.getRoomsWithMembers().map { it.map(RoomWithMembers::asExternalModel) }

    override fun createRoom(personalOptions: PersonalOptions, commonOptions: CommonOptions, password: String) {
        TODO("Not yet implemented")
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

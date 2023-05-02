package com.purple.hello.domain.rooms

import com.purple.core.model.PersonalOptions
import com.purple.core.model.Room
import com.purple.core.model.Member
import com.purple.data.rooms.RoomRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRoomListUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    operator fun invoke(): Flow<List<Room>> = roomRepository.getRooms()

//        flow {
//            emit(
//                listOf(
//                    Room(1, PersonalOptions(1, "", ""), members = listOf(Member(1, "first", ""))),
//                    Room(2, PersonalOptions(2, "", ""), members = listOf(Member(2, "second", ""))),
//                ),
//            )
//            delay(2000L)
//            emit(listOf(Room(2, PersonalOptions(2, "", ""), members = listOf(Member(2, "second", "")))))
//            delay(2000L)
//            emit(listOf(Room(3, PersonalOptions(3, "", ""), members = listOf(Member(3, "third", "")))))
//        }
}
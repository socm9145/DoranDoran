package com.purple.hello.domain.rooms

import com.purple.core.model.PersonalOptions
import com.purple.core.model.Room
import com.purple.core.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRoomListUseCase @Inject constructor() {
    operator fun invoke(): Flow<List<Room>> = flow {
        emit(
            listOf(
                Room(1, PersonalOptions(1, "", ""), members = listOf(User(1, "first", ""))),
                Room(2, PersonalOptions(2,  "", ""), members = listOf(User(2, "second", ""))),
            ),
        )
        delay(2000L)
        emit(listOf(Room(2, PersonalOptions(2, "", ""), members = listOf(User(2, "second", "")))))
        delay(2000L)
        emit(listOf(Room(3, PersonalOptions(3, "", ""), members = listOf(User(3, "third", "")))))
    }
}

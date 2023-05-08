package com.purple.data.rooms.di

import com.purple.data.rooms.repository.RoomRepository
import com.purple.data.rooms.repository.RoomRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface Module {

    @Binds
    fun bindsRoomRepository(
        roomRepositoryImpl: RoomRepositoryImpl,
    ): RoomRepository
}

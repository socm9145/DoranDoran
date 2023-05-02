package com.purple.data.rooms.di

import com.purple.data.rooms.service.RoomService
import com.purple.hello.core.network.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideRoomService(): RoomService =
        RetrofitClient.getInstance().create(RoomService::class.java)
}

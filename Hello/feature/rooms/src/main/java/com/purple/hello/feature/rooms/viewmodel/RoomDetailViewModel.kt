package com.purple.hello.feature.rooms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.purple.hello.feature.rooms.navigation.roomIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val roomId: Long? = savedStateHandle[roomIdArg]
}

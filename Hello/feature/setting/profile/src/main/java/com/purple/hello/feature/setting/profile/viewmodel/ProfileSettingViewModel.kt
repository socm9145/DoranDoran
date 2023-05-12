package com.purple.hello.feature.setting.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.hello.domain.user.ProfileSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSettingViewModel @Inject constructor(
    private val profileSettingUseCase: ProfileSettingUseCase,
) : ViewModel() {

    // TODO : Fake
    val isFirst = true

    fun setProfile(newProfileUrl: String, newBirth: String) =
        viewModelScope.launch(Dispatchers.IO) {
            profileSettingUseCase(newProfileUrl = newProfileUrl, newBirth = newBirth)
        }
}

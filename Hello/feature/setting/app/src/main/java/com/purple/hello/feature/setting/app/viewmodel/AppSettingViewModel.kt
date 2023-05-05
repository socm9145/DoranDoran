package com.purple.hello.feature.setting.app.viewmodel

import androidx.lifecycle.ViewModel
import com.purple.hello.domain.account.LogoutUseCase
import com.purple.hello.domain.account.DeleteAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppSettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
) : ViewModel() {
//    TODO
}

package com.purple.hello.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.hello.domain.account.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    fun googleLogin(idToken: String) {
        viewModelScope.launch {
            loginUseCase.loginWithGoogle(idToken)
        }
    }

    fun kakaoLogin(accessToken: String) {
        viewModelScope.launch {
            loginUseCase.loginWithKakao(accessToken)
        }
    }
}

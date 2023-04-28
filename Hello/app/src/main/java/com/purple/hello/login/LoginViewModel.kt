package com.purple.hello.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.hello.domain.account.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
//    private val context: Context,
) : ViewModel() {

//    private val accountDataStore: DataStore<AccountData> by lazy {
//        context.accountDataStore
//    }

    fun kakaoLogin(accessToken: String) {
        viewModelScope.launch {
//            val accountTokenResponse = loginUseCase.loginWithKakao(accessToken)
//            val accountData = AccountDataSerializer.toAccountData(accountTokenResponse)
//            accountDataStore.updateData { accountData }
////            loginUseCase.loginWithKakao(accessToken)
//        }
        }

        fun googleLogin(idToken: String) {
            viewModelScope.launch {
                loginUseCase.loginWithGoogle(idToken)
            }
        }
    }
}

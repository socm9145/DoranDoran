package com.purple.hello.domain.account

import com.purple.hello.data.account.AccountRepository
import com.purple.hello.data.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
) {
    suspend fun loginWithGoogle(idToken: String) {
        accountRepository.loginWithGoogle(idToken)
        userRepository.getUserInfo()
    }

    suspend fun loginWithKakao(accessToken: String) {
        accountRepository.loginWithKakao(accessToken)
        userRepository.getUserInfo()
    }
}

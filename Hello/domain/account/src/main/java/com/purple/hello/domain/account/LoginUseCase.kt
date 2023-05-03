package com.purple.hello.domain.account

import com.purple.hello.data.account.repository.AccountRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend fun loginWithGoogle(idToken: String) {
//        accountRepository.loginWithGoogle(idToken)
    }

    suspend fun loginWithKakao(accessToken: String) {
//        accountRepository.loginWithKakao(accessToken)
    }
}

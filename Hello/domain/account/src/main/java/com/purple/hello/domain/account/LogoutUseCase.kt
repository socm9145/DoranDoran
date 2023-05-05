package com.purple.hello.domain.account

import com.purple.hello.data.account.repository.AccountRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
//    TODO
//    suspend fun invoke()
}

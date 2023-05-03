package com.purple.hello.domain.account

import com.purple.hello.data.account.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckLoggedInUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(): Flow<Boolean> = accountRepository.isLoggedIn()
}

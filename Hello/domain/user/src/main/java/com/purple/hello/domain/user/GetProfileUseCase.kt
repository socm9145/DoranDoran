package com.purple.hello.domain.user

import com.purple.hello.data.user.repository.UserRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke() =
        userRepository.getProfile()
}

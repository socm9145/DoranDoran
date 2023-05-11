package com.purple.hello.domain.user

import com.purple.hello.data.user.repository.UserRepository
import java.util.*
import javax.inject.Inject

class ProfileSettingUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(newProfileUrl: String, newBirth: String) =
        userRepository.setProfile(newProfileUrl, newBirth)
}

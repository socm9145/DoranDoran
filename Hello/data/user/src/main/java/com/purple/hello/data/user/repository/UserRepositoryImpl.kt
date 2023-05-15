package com.purple.hello.data.user.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.purple.core.database.dao.UserDao
import com.purple.core.database.entity.MemberEntity
import com.purple.core.model.Profile
import com.purple.hello.core.datastore.UserDataStore
import com.purple.hello.core.network.utils.birthToDate
import com.purple.hello.data.user.datasource.RemoteUserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val userDao: UserDao,
    private val userDataStore: UserDataStore,
) : UserRepository {

    override suspend fun fetchUserInfo() {
        runCatching {
            remoteUserDataSource.getUserInfo()
        }.onFailure {
            throw it
        }.getOrThrow().let {
            val response = checkNotNull(it.body())
            withContext(Dispatchers.IO) {
                userDao.insertMember(
                    MemberEntity(
                        response.birth?.birthToDate(),
                        response.userId,
                        response.profileUrl,
                    ),
                )
            }
            userDataStore.setUserId(response.userId)
        }
    }

    override suspend fun setProfile(profileUrl: String, birth: String) {
        remoteUserDataSource.setProfile(
            profileUrl,
            birth,
        ).let {
            if (it.isSuccessful) {
                userDao.updateMember(
                    MemberEntity(
                        profileUrl = profileUrl,
                        birth = birth.birthToDate(),
                        userId = runBlocking { userDataStore.userId.first() },
                    ),
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getProfile(): Flow<Profile> {
        val userId = runBlocking { userDataStore.userId.first() }
        return userDao.getProfileUrl(userId).map {
            Profile(
                profileUrl = it.profileUrl,
                birth = LocalDateTime.now(),
            )
        }
    }
}

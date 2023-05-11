package com.purple.hello.data.user

import android.os.Build
import androidx.annotation.RequiresApi
import com.purple.core.database.dao.UserDao
import com.purple.core.database.entity.MemberEntity
import com.purple.hello.core.datastore.UserDataStore
import com.purple.hello.core.network.utils.toLocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val userDao: UserDao,
    private val userDataStore: UserDataStore,
) : UserRepository {

    @RequiresApi(Build.VERSION_CODES.O)
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
                        response.birth?.toLocalDateTime(),
                        response.userId,
                        response.profileUrl,
                    ),
                )
            }
            userDataStore.setUserId(response.userId)
        }
    }

    override fun getUserId(): Flow<Long> = userDataStore.userId
}

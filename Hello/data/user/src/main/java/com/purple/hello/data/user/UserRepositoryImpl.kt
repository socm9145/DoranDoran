package com.purple.hello.data.user

import com.purple.core.database.dao.UserDao
import com.purple.core.database.entity.MemberEntity
import com.purple.hello.core.datastore.UserDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val userDao: UserDao,
    private val userDataStore: UserDataStore,
) : UserRepository {

    override suspend fun getUserInfo() {
        runCatching {
            remoteUserDataSource.getUserInfo()
        }.onFailure {
            throw it
        }.getOrThrow().let {
            val response = checkNotNull(it.body())
            withContext(Dispatchers.IO) {
                userDao.insertMember(
                    MemberEntity(
                        response.birth,
                        response.userId,
                        response.profileUrl,
                    ),
                )
            }
            userDataStore.setUserId(response.userId)
        }
    }
}

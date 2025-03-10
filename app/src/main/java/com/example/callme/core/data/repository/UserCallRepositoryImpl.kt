package com.example.callme.core.data.repository

import com.example.callme.core.data.data_source.UserCallDao
import com.example.callme.core.domain.model.UserCall
import com.example.callme.core.domain.repository.UserCallRepository
import kotlinx.coroutines.flow.Flow

class UserCallRepositoryImpl(
    private val userCallDao: UserCallDao
) : UserCallRepository {

    override suspend fun insertUserCall(userCall: UserCall) {
        userCallDao.insertUserCall(userCall)
    }

    override fun getUserCalls(): Flow<List<UserCall>> {
        return userCallDao.getUserCalls()
    }
}

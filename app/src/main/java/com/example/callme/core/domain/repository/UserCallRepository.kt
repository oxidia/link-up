package com.example.callme.core.domain.repository

import com.example.callme.core.domain.model.UserCall
import kotlinx.coroutines.flow.Flow

interface UserCallRepository {

    suspend fun insertUserCall(userCall: UserCall)

    fun getUserCalls(): Flow<List<UserCall>>
}

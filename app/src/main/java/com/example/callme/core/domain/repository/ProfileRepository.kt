package com.example.callme.core.domain.repository

import com.example.callme.core.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun insertProfile(profile: Profile)

    fun getProfile(): Flow<Profile?>
}

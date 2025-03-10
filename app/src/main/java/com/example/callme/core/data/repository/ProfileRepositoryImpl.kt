package com.example.callme.core.data.repository

import com.example.callme.core.data.data_source.ProfileDao
import com.example.callme.core.domain.model.Profile
import com.example.callme.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl(
    private val profileDao: ProfileDao,
) : ProfileRepository {

    override suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile)
    }

    override fun getProfile(): Flow<Profile?> {
        return flow {
            profileDao.getProfiles()
                .collect {
                    if (it.isEmpty()) {
                        emit(null)
                    } else {
                        emit(it[0])
                    }
                }
        }
    }
}

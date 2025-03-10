package com.example.callme.feature_connect.presentation

import com.example.callme.core.domain.model.Profile
import com.example.callme.core.domain.model.UserCall

data class ConnectState(
    val username: String = "",
    val currentProfile: Profile? = null,
    val userCalls: List<UserCall> = emptyList(),
)

package com.example.callme.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_calls",
)
data class UserCall(
    @PrimaryKey
    val id: Int? = null,

    val startedAt: Long,
    val endedAt: Long,
    val createdAt: Long = System.currentTimeMillis()
)

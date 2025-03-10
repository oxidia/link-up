package com.example.callme.core.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "profiles",
    indices = [
        Index(
            value = ["username"],
            unique = true
        )
    ]
)
data class Profile(
    @PrimaryKey
    val id: Int? = null,

    val username: String,
)

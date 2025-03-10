package com.example.callme.core.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.callme.core.domain.model.UserCall
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCallDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserCall(userCall: UserCall)

    @Query("SELECT * FROM user_calls ORDER BY createdAt DESC")
    fun getUserCalls(): Flow<List<UserCall>>
}

package com.example.callme.core.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.callme.core.domain.model.Profile
import com.example.callme.core.domain.model.UserCall

@Database(
    entities = [Profile::class, UserCall::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class Database : RoomDatabase() {

    abstract val profileDao: ProfileDao
    abstract val userCallDao: UserCallDao

    companion object {

        const val DATABASE_NAME = "calls_db"

        val migration1To2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                CREATE TABLE IF NOT EXISTS `user_calls` (
                    `id` INTEGER,
                    `startedAt` INTEGER NOT NULL,
                    `endedAt` INTEGER NOT NULL,
                    `createdAt` INTEGER NOT NULL,
                    PRIMARY KEY(`id`)
                )
            """.trimIndent())
            }
        }
    }
}

package com.example.callme.di

import android.app.Application
import androidx.room.Room
import com.example.callme.core.data.data_source.Database
import com.example.callme.core.data.repository.ProfileRepositoryImpl
import com.example.callme.core.data.repository.UserCallRepositoryImpl
import com.example.callme.core.domain.repository.ProfileRepository
import com.example.callme.core.domain.repository.UserCallRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database {
        return Room.databaseBuilder(
            context = app,
            klass = Database::class.java,
            name = Database.DATABASE_NAME
        )
            .addMigrations(Database.migration1To2)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: Database): ProfileRepository {
        return ProfileRepositoryImpl(
            profileDao = database.profileDao
        )
    }

    @Provides
    @Singleton
    fun provideUserCallRepository(database: Database): UserCallRepository {
        return UserCallRepositoryImpl(
            userCallDao = database.userCallDao
        )
    }
}

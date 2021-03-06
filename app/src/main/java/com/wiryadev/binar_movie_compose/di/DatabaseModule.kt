package com.wiryadev.binar_movie_compose.di

import android.content.Context
import androidx.room.Room
import com.wiryadev.binar_movie_compose.data.local.db.BinarMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): BinarMovieDatabase = Room
        .databaseBuilder(
            appContext,
            BinarMovieDatabase::class.java,
            "binar_movie_db"
        )
        .build()

    @Singleton
    @Provides
    fun provideUserDao(database: BinarMovieDatabase) = database.userDao()

}
package com.wiryadev.binar_movie_compose.di

import com.wiryadev.binar_movie_compose.data.local.UserLocalDataSource
import com.wiryadev.binar_movie_compose.data.local.UserLocalDataSourceImpl
import com.wiryadev.binar_movie_compose.data.preference.UserPreferenceDataSource
import com.wiryadev.binar_movie_compose.data.preference.UserPreferenceDataSourceImpl
import com.wiryadev.binar_movie_compose.data.remote.movie.MovieRemoteDataSource
import com.wiryadev.binar_movie_compose.data.remote.movie.MovieRemoteDataSourceImpl
import com.wiryadev.binar_movie_compose.data.remote.tv.TvRemoteDataSource
import com.wiryadev.binar_movie_compose.data.remote.tv.TvRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsMovieRemoteDataSource(
        movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsTvRemoteDataSource(
        tvRemoteDataSourceImpl: TvRemoteDataSourceImpl
    ): TvRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsUserPreferenceDataSource(
        userPreferenceDataStoreImpl: UserPreferenceDataSourceImpl
    ): UserPreferenceDataSource

}
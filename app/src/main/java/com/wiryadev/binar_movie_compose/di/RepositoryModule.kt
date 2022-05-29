package com.wiryadev.binar_movie_compose.di

import com.wiryadev.binar_movie_compose.data.repositories.movie.MovieRepository
import com.wiryadev.binar_movie_compose.data.repositories.movie.MovieRepositoryImpl
import com.wiryadev.binar_movie_compose.data.repositories.tv.TvRepository
import com.wiryadev.binar_movie_compose.data.repositories.tv.TvRepositoryImpl
import com.wiryadev.binar_movie_compose.data.repositories.user.UserRepository
import com.wiryadev.binar_movie_compose.data.repositories.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindsMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindsTvRepository(repository: TvRepositoryImpl): TvRepository

}
package com.wiryadev.binar_movie_compose.data.remote.movie

import com.wiryadev.binar_movie_compose.data.remote.movie.dto.DetailMovieResponse
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.ListMovieResponse

interface MovieRemoteDataSource {

    suspend fun discoverMovies(
        page: Int,
        size: Int,
    ): ListMovieResponse


    suspend fun getMovieDetail(
        movieId: Int
    ): DetailMovieResponse

}
package com.wiryadev.binar_movie_compose.data.repositories.movie

import androidx.paging.PagingData
import com.wiryadev.binar_movie_compose.data.remote.Result
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.DetailMovieResponse
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun discoverMovies(): Flow<PagingData<MovieDto>>

    fun getMovieDetail(movieId: Int): Flow<Result<DetailMovieResponse>>

}
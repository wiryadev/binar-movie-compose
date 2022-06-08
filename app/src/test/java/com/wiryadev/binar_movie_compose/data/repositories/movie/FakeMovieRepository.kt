package com.wiryadev.binar_movie_compose.data.repositories.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wiryadev.binar_movie_compose.MovieDataDummy
import com.wiryadev.binar_movie_compose.data.remote.Result
import com.wiryadev.binar_movie_compose.data.remote.movie.MovieRemoteDataSource
import com.wiryadev.binar_movie_compose.data.remote.movie.MoviesPagingSource
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.DetailMovieResponse
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.MovieDto
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieRepository : MovieRepository {

    private val remoteDataSource = mockk<MovieRemoteDataSource>()

    override fun discoverMovies(): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                MoviesPagingSource(remoteDataSource = remoteDataSource)
            }
        ).flow
    }

    override fun getMovieDetail(movieId: Int): Flow<Result<DetailMovieResponse>> {
        return if (movieId == 999) {
            flowOf(Result.Error(RuntimeException("Movie Not Found")))
        } else {
            flowOf(Result.Success(MovieDataDummy.generateDetailMovieResponse(movieId)))
        }
    }

}
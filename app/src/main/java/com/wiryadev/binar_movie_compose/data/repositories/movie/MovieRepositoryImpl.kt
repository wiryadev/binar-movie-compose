package com.wiryadev.binar_movie_compose.data.repositories.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wiryadev.binar_movie_compose.data.remote.Result
import com.wiryadev.binar_movie_compose.data.remote.movie.MovieRemoteDataSource
import com.wiryadev.binar_movie_compose.data.remote.movie.MoviesPagingSource
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.DetailMovieResponse
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.MovieDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
) : MovieRepository {

    override fun discoverMovies(): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_PAGE_SIZE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(remoteDataSource = remoteDataSource)
            }
        ).flow
    }

    override fun getMovieDetail(movieId: Int): Flow<Result<DetailMovieResponse>> {
        return flow {
            try {
                val response = remoteDataSource.getMovieDetail(movieId = movieId)
                emit(Result.Success(data = response))
            } catch (e: Exception) {
                emit(Result.Error(exception = e))
            }
        }.flowOn(Dispatchers.IO)
    }

}

private const val PAGING_PAGE_SIZE = 20
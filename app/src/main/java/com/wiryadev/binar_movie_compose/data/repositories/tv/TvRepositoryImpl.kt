package com.wiryadev.binar_movie_compose.data.repositories.tv

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wiryadev.binar_movie_compose.data.remote.Result
import com.wiryadev.binar_movie_compose.data.remote.tv.TvPagingSource
import com.wiryadev.binar_movie_compose.data.remote.tv.TvRemoteDataSource
import com.wiryadev.binar_movie_compose.data.remote.tv.dto.DetailTvResponse
import com.wiryadev.binar_movie_compose.data.remote.tv.dto.TvDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TvRepositoryImpl @Inject constructor(
    private val remoteDataSource: TvRemoteDataSource,
) : TvRepository {

    override fun discoverTvShows(): Flow<PagingData<TvDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_PAGE_SIZE
            ),
            pagingSourceFactory = {
                TvPagingSource(remoteDataSource = remoteDataSource)
            }
        ).flow
    }

    override fun getTvShowDetail(tvId: Int): Flow<Result<DetailTvResponse>> {
        return flow {
            try {
                val response = remoteDataSource.getTvDetail(tvId = tvId)
                emit(Result.Success(data = response))
            } catch (e: Exception) {
                emit(Result.Error(exception = e))
            }
        }.flowOn(Dispatchers.IO)
    }

}

private const val PAGING_PAGE_SIZE = 20
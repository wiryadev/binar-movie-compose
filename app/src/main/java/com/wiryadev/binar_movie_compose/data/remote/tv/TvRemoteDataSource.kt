package com.wiryadev.binar_movie_compose.data.remote.tv

import com.wiryadev.binar_movie_compose.data.remote.tv.dto.DetailTvResponse
import com.wiryadev.binar_movie_compose.data.remote.tv.dto.ListTvResponse

interface TvRemoteDataSource {

    suspend fun discoverTv(
        page: Int,
        size: Int,
    ): ListTvResponse


    suspend fun getTvDetail(
        tvId: Int
    ): DetailTvResponse

}
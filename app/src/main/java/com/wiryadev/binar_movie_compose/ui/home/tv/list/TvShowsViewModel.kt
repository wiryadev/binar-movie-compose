package com.wiryadev.binar_movie_compose.ui.home.tv.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wiryadev.binar_movie_compose.data.remote.tv.dto.TvDto
import com.wiryadev.binar_movie_compose.data.repositories.tv.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val tvRepository: TvRepository
) : ViewModel() {

    val tvShows: Flow<PagingData<TvDto>> =
        tvRepository.discoverTvShows().cachedIn(viewModelScope)

}
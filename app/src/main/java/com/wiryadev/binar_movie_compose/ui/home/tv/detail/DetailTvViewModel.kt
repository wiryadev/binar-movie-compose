package com.wiryadev.binar_movie_compose.ui.home.tv.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.binar_movie_compose.data.local.entity.TvEntity
import com.wiryadev.binar_movie_compose.data.remote.Result
import com.wiryadev.binar_movie_compose.data.remote.tv.dto.DetailTvResponse
import com.wiryadev.binar_movie_compose.data.repositories.tv.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTvViewModel @Inject constructor(
    private val tvRepository: TvRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailTvUiState> =
        MutableStateFlow(DetailTvUiState())
    val uiState: StateFlow<DetailTvUiState> get() = _uiState.asStateFlow()

    fun getDetail(tvId: Int) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            tvRepository.getTvShowDetail(tvId = tvId).collectLatest { result ->
                when (result) {
                    is Result.Success -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            tv = result.data
                        )
                    }
                    is Result.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                    }
                }
            }
        }
    }

    fun checkIsFavorite(id: Int) = viewModelScope.launch {
        tvRepository.checkFavoriteTv(id = id).collect { tvShow ->
            _uiState.update {
                it.copy(
                    isFavorite = tvShow > 0
                )
            }
        }
    }

    fun addFavoriteTv(tv: DetailTvResponse) = viewModelScope.launch {
        tvRepository.addFavoriteTv(
            tv = TvEntity(
                tvId = tv.id,
                title = tv.name,
                posterPath = tv.posterPath,
            )
        )
    }

    fun deleteFavoriteTv(tv: DetailTvResponse) = viewModelScope.launch {
        tvRepository.deleteFavoriteTv(
            tv = TvEntity(
                tvId = tv.id,
                title = tv.name,
                posterPath = tv.posterPath,
            )
        )
    }
}

data class DetailTvUiState(
    val isLoading: Boolean = false,
    val tv: DetailTvResponse? = null,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null,
)
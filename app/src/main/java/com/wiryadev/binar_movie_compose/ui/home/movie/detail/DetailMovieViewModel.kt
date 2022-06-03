package com.wiryadev.binar_movie_compose.ui.home.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.binar_movie_compose.data.remote.Result
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.DetailMovieResponse
import com.wiryadev.binar_movie_compose.data.repositories.movie.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailMovieUiState> =
        MutableStateFlow(DetailMovieUiState())
    val uiState: StateFlow<DetailMovieUiState> get() = _uiState.asStateFlow()

    fun getDetail(movieId: Int) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            movieRepository.getMovieDetail(movieId = movieId).collectLatest { result ->
                when (result) {
                    is Result.Success -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            movie = result.data
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

}

data class DetailMovieUiState(
    val isLoading: Boolean = false,
    val movie: DetailMovieResponse? = null,
    val errorMessage: String? = null,
)
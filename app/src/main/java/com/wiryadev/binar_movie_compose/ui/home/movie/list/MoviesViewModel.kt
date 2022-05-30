package com.wiryadev.binar_movie_compose.ui.home.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.MovieDto
import com.wiryadev.binar_movie_compose.data.repositories.movie.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movies: Flow<PagingData<MovieDto>> =
        movieRepository.discoverMovies().cachedIn(viewModelScope)

}
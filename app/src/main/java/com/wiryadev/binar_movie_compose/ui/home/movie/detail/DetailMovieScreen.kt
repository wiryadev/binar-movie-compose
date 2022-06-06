package com.wiryadev.binar_movie_compose.ui.home.movie.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.ui.components.GenericDetailScreen
import com.wiryadev.binar_movie_compose.ui.components.LoadingScreen

@ExperimentalMaterial3Api
@Composable
fun DetailMovieScreen(
    id: Int,
    viewModel: DetailMovieViewModel,
) {
    LaunchedEffect(key1 = id) {
        viewModel.getDetail(id)
    }
    
    val uiState by viewModel.uiState.collectAsState()
    val movie = uiState.movie

    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        movie?.let {
            GenericDetailScreen(
                title = movie.title,
                posterPath = movie.posterPath,
                genres = movie.genres.map { it.name },
                rating = movie.voteAverage.toString(),
                dateLabel = stringResource(id = R.string.release_date),
                dateData = movie.releaseDate,
                tagline = movie.tagline,
                overview = movie.overview,
            )
        }
    }
}
package com.wiryadev.binar_movie_compose.ui.home.tv.detail

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
fun DetailTvScreen(
    id: Int,
    viewModel: DetailTvViewModel,
) {
    LaunchedEffect(key1 = id) {
        viewModel.getDetail(id)
    }

    val uiState by viewModel.uiState.collectAsState()
    val tv = uiState.tv

    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        tv?.let {
            GenericDetailScreen(
                title = tv.name,
                posterPath = tv.posterPath,
                genres = tv.genres.map { it.name },
                rating = tv.voteAverage.toString(),
                dateLabel = stringResource(id = R.string.first_air_date),
                dateData = tv.firstAirDate,
                tagline = tv.tagline,
                overview = tv.overview,
            )
        }
    }
}
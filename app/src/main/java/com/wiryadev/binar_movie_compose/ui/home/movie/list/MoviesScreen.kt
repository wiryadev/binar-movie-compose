package com.wiryadev.binar_movie_compose.ui.home.movie.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.MovieDto
import com.wiryadev.binar_movie_compose.ui.components.MovieCard
import kotlinx.coroutines.flow.Flow

@ExperimentalMaterial3Api
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        MovieList(moviesPagingData = viewModel.movies, onMovieClick = onMovieClick)
    }
}

@ExperimentalMaterial3Api
@Composable
fun MovieList(
    moviesPagingData: Flow<PagingData<MovieDto>>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val movies = moviesPagingData.collectAsLazyPagingItems()
    val errorState = movies.loadState.refresh as? LoadState.Error
        ?: movies.loadState.append as? LoadState.Error

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        items(
            items = movies,
            key = { movie -> movie.id }
        ) { movie ->
            movie?.let {
                MovieCard(
                    movie = it,
                    onClick = onMovieClick,
                )
            }
        }
    }
}
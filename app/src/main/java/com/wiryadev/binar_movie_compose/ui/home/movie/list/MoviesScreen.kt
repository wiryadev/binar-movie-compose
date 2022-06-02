package com.wiryadev.binar_movie_compose.ui.home.movie.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.wiryadev.binar_movie_compose.ui.components.ErrorCard
import com.wiryadev.binar_movie_compose.ui.components.GenericErrorScreen
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
        MovieList(
            moviesPagingData = viewModel.movies,
            onMovieClick = onMovieClick,
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun MovieList(
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
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        items(
            items = movies,
            key = { movie -> movie.id }
        ) { movie ->
            movie?.let {
                MovieCard(
                    movie = it,
                    isLoading = false,
                    onClick = { id ->
                        onMovieClick(id)
                    },
                )
            }
        }

        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    items(10) {
                        MovieCard(
                            movie = dummyMovie,
                            isLoading = true,
                            onClick = {}
                        )
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        MovieCard(
                            movie = dummyMovie,
                            isLoading = true,
                            onClick = {}
                        )
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    item {
                        GenericErrorScreen(
                            message = errorState?.error?.message.toString(),
                            modifier = Modifier.fillParentMaxSize(),
                            onClick = ::retry,
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        ErrorCard(
                            message = errorState?.error?.message.toString(),
                            onClick = ::retry,
                        )
                    }
                }
            }
        }
    }
}

private val dummyMovie = MovieDto(
    adult = false,
    backdropPath = "/backdropMovie",
    genreIds = listOf(),
    id = 1,
    originalLanguage = "",
    originalTitle = "Spider Man: No Way Home",
    overview = "",
    popularity = 0.0,
    posterPath = "/posterMovie",
    releaseDate = "",
    title = "Spider Man: No Way Home",
    video = false,
    voteAverage = 0.0,
    voteCount = 0
)
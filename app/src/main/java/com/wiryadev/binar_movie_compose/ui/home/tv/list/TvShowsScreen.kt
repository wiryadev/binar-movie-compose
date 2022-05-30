package com.wiryadev.binar_movie_compose.ui.home.tv.list

import androidx.compose.foundation.layout.Arrangement
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
import com.wiryadev.binar_movie_compose.data.remote.tv.dto.TvDto
import com.wiryadev.binar_movie_compose.ui.components.TvCard
import kotlinx.coroutines.flow.Flow

@ExperimentalMaterial3Api
@Composable
fun TvShowsScreen(
    viewModel: TvShowsViewModel,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        TvList(tvShowsPagingData = viewModel.tvShows, onMovieClick = onMovieClick)
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TvList(
    tvShowsPagingData: Flow<PagingData<TvDto>>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tvShows = tvShowsPagingData.collectAsLazyPagingItems()
    val errorState = tvShows.loadState.refresh as? LoadState.Error
        ?: tvShows.loadState.append as? LoadState.Error

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        items(
            items = tvShows,
            key = { tv -> tv.id }
        ) { movie ->
            movie?.let {
                TvCard(
                    tv = it,
                    onClick = onMovieClick,
                )
            }
        }
    }
}
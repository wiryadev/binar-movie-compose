package com.wiryadev.binar_movie_compose.ui.home.tv.list

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
import com.wiryadev.binar_movie_compose.data.remote.tv.dto.TvDto
import com.wiryadev.binar_movie_compose.ui.components.ErrorCard
import com.wiryadev.binar_movie_compose.ui.components.GenericErrorScreen
import com.wiryadev.binar_movie_compose.ui.components.TvCard
import kotlinx.coroutines.flow.Flow

@ExperimentalMaterial3Api
@Composable
fun TvShowsScreen(
    viewModel: TvShowsViewModel,
    onTvClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        TvList(
            tvShowsPagingData = viewModel.tvShows,
            onTvClick = onTvClick,
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TvList(
    tvShowsPagingData: Flow<PagingData<TvDto>>,
    onTvClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tvShows = tvShowsPagingData.collectAsLazyPagingItems()
    val errorState = tvShows.loadState.refresh as? LoadState.Error
        ?: tvShows.loadState.append as? LoadState.Error

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        items(
            items = tvShows,
            key = { tv -> tv.id }
        ) { movie ->
            movie?.let {
                TvCard(
                    tv = it,
                    isLoading = false,
                    onClick = { id ->
                        onTvClick(id)
                    },
                )
            }
        }

        tvShows.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    items(10) {
                        TvCard(
                            tv = dummyTv,
                            isLoading = true,
                            onClick = {}
                        )
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        TvCard(
                            tv = dummyTv,
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

private val dummyTv = TvDto(
    backdropPath = "/backdropMovie",
    genreIds = listOf(),
    id = 1,
    originalLanguage = "",
    originalName = "Spider Man: No Way Home",
    overview = "",
    popularity = 0.0,
    posterPath = "/posterMovie",
    firstAirDate = "",
    name = "Spider Man: No Way Home",
    voteAverage = 0.0,
    voteCount = 0,
    originCountry = listOf("ID")
)
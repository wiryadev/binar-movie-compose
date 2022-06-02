package com.wiryadev.binar_movie_compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.wiryadev.binar_movie_compose.BuildConfig
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.MovieDto
import com.wiryadev.binar_movie_compose.data.remote.tv.dto.TvDto
import com.wiryadev.binar_movie_compose.ui.theme.BinarMovieComposeTheme

@ExperimentalMaterial3Api
@Composable
fun MovieCard(
    movie: MovieDto,
    isLoading: Boolean,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    GenericCard(
        id = movie.id,
        posterUrl = "${BuildConfig.BASE_IMAGE_URL}${movie.posterPath}",
        title = movie.title,
        date = movie.releaseDate,
        rating = movie.voteAverage.toString(),
        isLoading = isLoading,
        onClick = onClick,
        modifier = modifier,
    )
}

@ExperimentalMaterial3Api
@Composable
fun TvCard(
    tv: TvDto,
    isLoading: Boolean,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    GenericCard(
        id = tv.id,
        posterUrl = "${BuildConfig.BASE_IMAGE_URL}${tv.posterPath}",
        title = tv.name,
        date = tv.firstAirDate,
        rating = tv.voteAverage.toString(),
        isLoading = isLoading,
        onClick = onClick,
        modifier = modifier,
    )
}

@ExperimentalMaterial3Api
@Composable
private fun GenericCard(
    id: Int,
    posterUrl: String,
    title: String,
    date: String,
    rating: String,
    isLoading: Boolean,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth(),
        onClick = {
            onClick.invoke(id)
        },
        colors = CardDefaults.outlinedCardColors(),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        val painter = rememberAsyncImagePainter(model = posterUrl)
        val isImageLoading = painter.state is AsyncImagePainter.State.Loading

        Row {
            Image(
                painter = painter,
                contentDescription = stringResource(id = R.string.poster),
                modifier = Modifier
                    .width(100.dp)
                    .height(160.dp)
                    .placeholder(
                        visible = isLoading || isImageLoading,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier.padding(all = 16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    modifier = Modifier
                        .placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                )
                SuggestionChip(
                    onClick = { /* do nothing */ },
                    label = {
                        Text(text = rating)
                    }
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ErrorCard(
    message: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.outlinedCardColors(),
        border = CardDefaults.outlinedCardBorder(),
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.error
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onClick) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun MovieItemPreview() {
    val movieDto = MovieDto(
        adult = false,
        backdropPath = "/backdropMovie",
        genreIds = listOf(),
        id = 12345,
        originalLanguage = "",
        originalTitle = "",
        overview = "",
        popularity = 0.0,
        posterPath = "/YksR65as1ppF2N48TJAh2PLamX.jpg",
        releaseDate = "2022-03-30",
        title = "Moon Knight",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )

    BinarMovieComposeTheme {
        MovieCard(movie = movieDto, isLoading = false, onClick = { })
    }
}
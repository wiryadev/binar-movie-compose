package com.wiryadev.binar_movie_compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.wiryadev.binar_movie_compose.BuildConfig
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.MovieDto
import com.wiryadev.binar_movie_compose.data.remote.tv.dto.TvDto
import com.wiryadev.binar_movie_compose.ui.theme.BinarMovieComposeTheme

@ExperimentalMaterial3Api
@Composable
fun MovieCard(
    movie: MovieDto,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GenericCard(
        posterUrl = "${BuildConfig.BASE_IMAGE_URL}${movie.posterPath}",
        title = movie.title,
        date = movie.releaseDate,
        rating = movie.voteAverage.toString(),
        onClick = onClick,
        modifier = modifier,
    )
}

@ExperimentalMaterial3Api
@Composable
fun TvCard(
    tv: TvDto,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GenericCard(
        posterUrl = "${BuildConfig.BASE_IMAGE_URL}${tv.posterPath}",
        title = tv.name,
        date = tv.firstAirDate,
        rating = tv.voteAverage.toString(),
        onClick = onClick,
        modifier = modifier,
    )
}

@ExperimentalMaterial3Api
@Composable
private fun GenericCard(
    posterUrl: String,
    title: String,
    date: String,
    rating: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(model = posterUrl),
                contentDescription = "",
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp),
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier.padding(all = 16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelMedium,
                )
                SuggestionChip(
                    onClick = { /* do nothing */ },
                    label = {
                        Text(
                            text = rating,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                )
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
        MovieCard(movie = movieDto, onClick = { })
    }
}
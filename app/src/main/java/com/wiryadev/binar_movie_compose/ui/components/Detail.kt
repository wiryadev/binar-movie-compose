package com.wiryadev.binar_movie_compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.wiryadev.binar_movie_compose.BuildConfig
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.ui.theme.BinarMovieComposeTheme

@ExperimentalMaterial3Api
@Composable
fun GenericDetailScreen(
    title: String,
    posterPath: String,
    genres: List<String>,
    rating: String,
    dateLabel: String,
    dateData: String,
    tagline: String?,
    overview: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PosterWithRating(posterPath = posterPath, rating = rating)
            DetailHeader(title = title, genres = genres, dateLabel = dateLabel, dateData = dateData)
        }
        Spacer(modifier = Modifier.height(16.dp))
        TaglineAndOverview(tagline = tagline, overview = overview)
    }
}

@ExperimentalMaterial3Api
@Composable
private fun PosterWithRating(
    posterPath: String,
    rating: String,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(model = "${BuildConfig.BASE_IMAGE_URL}${posterPath}")
    val isImageLoading = painter.state is AsyncImagePainter.State.Loading

    Box(
        modifier = modifier
            .size(
                width = 128.dp,
                height = 192.dp,
            )
            .placeholder(
                visible = isImageLoading,
                highlight = PlaceholderHighlight.shimmer(),
            ),
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp)),
        )
        SuggestionChip(
            onClick = { /* do nothing */ },
            label = {
                Text(
                    text = rating,
                    style = MaterialTheme.typography.titleSmall,
                )
            },
            colors = SuggestionChipDefaults.elevatedSuggestionChipColors(),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun DetailHeader(
    title: String,
    genres: List<String>,
    dateLabel: String,
    dateData: String,
) {
    Column {
        Text(
            text = title,
            maxLines = 3,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.genre),
            fontSize = 12.sp,
        )
        Text(text = genres.joinToString(separator = ", "))

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = dateLabel,
            fontSize = 12.sp,
        )
        Text(text = dateData)
    }
}

@Composable
private fun TaglineAndOverview(
    tagline: String?,
    overview: String?,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = tagline ?: "",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = overview ?: "",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun DetailPreview() {
    BinarMovieComposeTheme {
        PosterWithRating(posterPath = "", rating = "6.7")
    }
}

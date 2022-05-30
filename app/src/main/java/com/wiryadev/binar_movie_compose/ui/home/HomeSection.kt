package com.wiryadev.binar_movie_compose.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.ui.home.movie.list.MoviesScreen
import com.wiryadev.binar_movie_compose.ui.home.tv.list.TvShowsScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.addHomeGraph(
    onMovieSelected: (Int, NavBackStackEntry) -> Unit,
    onTvSelected: (Int, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable(HomeSections.MOVIE.route) { from ->
        MoviesScreen(
            viewModel = hiltViewModel(),
            onMovieClick = { id ->
                onMovieSelected(id, from)
            },
            modifier = modifier,
        )
    }
    composable(HomeSections.TV.route) { from ->
        TvShowsScreen(
            viewModel = hiltViewModel(),
            onMovieClick = { id ->
                onTvSelected(id, from)
            },
            modifier = modifier,
        )
    }
    composable(HomeSections.PROFILE.route) {
        // ProfileScreen
    }
}

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    MOVIE(R.string.movies, Icons.Rounded.Movie, "home/movies"),
    TV(R.string.tv_shows, Icons.Rounded.Tv, "home/tv"),
    PROFILE(R.string.profile, Icons.Rounded.AccountCircle, "home/profile")
}

@Composable
fun BinarBottomBar(
    tabs: Array<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentSection = tabs.first { it.route == currentRoute }

    BottomAppBar(
        modifier = modifier.fillMaxWidth()
    ) {

        tabs.forEach { section ->
            val selected = section == currentSection
            val text = stringResource(section.title)

            NavigationBarItem(
                selected = selected,
                onClick = { navigateToRoute(section.route) },
                icon = {
                    Icon(
                        imageVector = section.icon,
                        contentDescription = text,
                    )
                },
                label = {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                    )
                },
            )
        }
    }
}
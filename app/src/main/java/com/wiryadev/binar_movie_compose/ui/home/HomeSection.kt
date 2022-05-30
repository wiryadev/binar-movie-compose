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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.core.os.ConfigurationCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wiryadev.binar_movie_compose.R
import java.util.Locale


fun NavGraphBuilder.addHomeGraph(
    modifier: Modifier = Modifier,
) {
    composable(HomeSections.MOVIE.route) {
        // MovieScreen
    }
    composable(HomeSections.TV.route) {
        // TvScreen
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

            val configuration = LocalConfiguration.current
            val currentLocale = ConfigurationCompat
                .getLocales(configuration)[0] ?: Locale.getDefault()
            val text = stringResource(section.title).uppercase(currentLocale)

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
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                    )
                },
            )
        }
    }
}
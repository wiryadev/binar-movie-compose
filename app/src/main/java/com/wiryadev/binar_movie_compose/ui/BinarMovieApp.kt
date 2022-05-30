package com.wiryadev.binar_movie_compose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wiryadev.binar_movie_compose.ui.home.BinarBottomBar
import com.wiryadev.binar_movie_compose.ui.home.HomeSections
import com.wiryadev.binar_movie_compose.ui.home.addHomeGraph

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val MOVIE_DETAIL_ROUTE = "movie"
    const val MOVIE_ID_KEY = "movieId"
    const val TV_DETAIL_ROUTE = "snack"
    const val TV_ID_KEY = "tvId"
}

@ExperimentalMaterial3Api
@Composable
fun BinarMovieApp() {
    val navController = rememberNavController()

    val bottomBarTabs = HomeSections.values()
    val bottomBarRoutes = bottomBarTabs.map { it.route }

    val shouldShowBottomBar = navController
        .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes
    val currentRoute = navController.currentDestination?.route ?: MainDestinations.HOME_ROUTE

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BinarBottomBar(
                    tabs = bottomBarTabs,
                    currentRoute = currentRoute,
                    navigateToRoute = {
                        navigateToBottomBarRoute(navController, currentRoute, it)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainDestinations.HOME_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            homeNavGraph(
                onMovieSelected = { id, from ->
                    navigateToMovieDetail(
                        navController = navController,
                        movieId = id,
                        from = from
                    )
                },
                onTvSelected = { id, from ->
                    navigateToTvDetail(
                        navController = navController,
                        tvId = id,
                        from = from
                    )
                },
                upPress = {
                    upPress(navController)
                }
            )
        }
    }
}

@ExperimentalMaterial3Api
private fun NavGraphBuilder.homeNavGraph(
    onMovieSelected: (Int, NavBackStackEntry) -> Unit,
    onTvSelected: (Int, NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.MOVIE.route
    ) {
        addHomeGraph(onMovieSelected, onTvSelected)
    }
    composable(
        "${MainDestinations.MOVIE_DETAIL_ROUTE}/{${MainDestinations.MOVIE_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.MOVIE_ID_KEY) { type = NavType.IntType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val snackId = arguments.getLong(MainDestinations.MOVIE_ID_KEY)
//        SnackDetail(snackId, upPress)
    }
}

fun upPress(navController: NavHostController) {
    navController.navigateUp()
}

fun navigateToBottomBarRoute(
    navController: NavHostController,
    currentRoute: String,
    route: String,
) {
    if (route != currentRoute) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
            // Pop up backstack to the first destination and save state. This makes going back
            // to the start destination when pressing back in any other bottom tab.
            popUpTo(findStartDestination(navController.graph).id) {
                saveState = true
            }
        }
    }
}

fun navigateToMovieDetail(
    navController: NavHostController,
    movieId: Int,
    from: NavBackStackEntry,
) {
    // In order to discard duplicated navigation events, we check the Lifecycle
    if (from.lifecycleIsResumed()) {
        navController.navigate("${MainDestinations.MOVIE_DETAIL_ROUTE}/$movieId")
    }
}

fun navigateToTvDetail(
    navController: NavHostController,
    tvId: Int,
    from: NavBackStackEntry,
) {
    // In order to discard duplicated navigation events, we check the Lifecycle
    if (from.lifecycleIsResumed()) {
        navController.navigate("${MainDestinations.TV_DETAIL_ROUTE}/$tvId")
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
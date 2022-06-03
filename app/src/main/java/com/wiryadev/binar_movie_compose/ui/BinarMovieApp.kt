package com.wiryadev.binar_movie_compose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wiryadev.binar_movie_compose.data.local.entity.UserEntity
import com.wiryadev.binar_movie_compose.ui.auth.AuthSections
import com.wiryadev.binar_movie_compose.ui.auth.login.LoginScreen
import com.wiryadev.binar_movie_compose.ui.auth.login.LoginViewModel
import com.wiryadev.binar_movie_compose.ui.auth.register.RegisterScreen
import com.wiryadev.binar_movie_compose.ui.auth.register.RegisterViewModel
import com.wiryadev.binar_movie_compose.ui.home.BinarBottomBar
import com.wiryadev.binar_movie_compose.ui.home.HomeSections
import com.wiryadev.binar_movie_compose.ui.home.addHomeGraph
import com.wiryadev.binar_movie_compose.ui.home.movie.detail.DetailMovieScreen
import com.wiryadev.binar_movie_compose.ui.home.movie.detail.DetailMovieViewModel
import com.wiryadev.binar_movie_compose.ui.home.tv.detail.DetailTvScreen
import com.wiryadev.binar_movie_compose.ui.home.tv.detail.DetailTvViewModel

object MainDestinations {
    const val AUTH_ROUTE = "auth"
    const val HOME_ROUTE = "home"
    const val MOVIE_DETAIL_ROUTE = "movie"
    const val MOVIE_ID_KEY = "movieId"
    const val TV_DETAIL_ROUTE = "snack"
    const val TV_ID_KEY = "tvId"
}

@ExperimentalMaterial3Api
@Composable
fun BinarMovieApp(
    isLoggedIn: Boolean,
) {
    val navController = rememberNavController()

    val bottomBarTabs = HomeSections.values()
    val bottomBarRoutes = bottomBarTabs.map { it.route }

    val startRoute = if (isLoggedIn) {
        MainDestinations.HOME_ROUTE
    } else MainDestinations.AUTH_ROUTE
    val shouldShowBottomBar = navController.currentBackStackEntryAsState().value
        ?.destination
        ?.route in bottomBarRoutes
    val currentRoute = navController.currentDestination?.route ?: startRoute

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
            startDestination = startRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            if (isLoggedIn) {
                mainNavGraph(
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
                )
            } else {
                authNavGraph(
                    navController = navController,
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
private fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = MainDestinations.AUTH_ROUTE,
        startDestination = AuthSections.LOGIN.route
    ) {
        composable(route = AuthSections.LOGIN.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onNavigateRegisterClicked = {
                    navController.navigate(route = AuthSections.REGISTER.route)
                }
            )
        }
        composable(route = AuthSections.REGISTER.route) {
            val viewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSubmitted = { username, email, password ->
                    viewModel.register(
                        UserEntity(
                            email = email,
                            username = username,
                            password = password,
                        )
                    )
                },
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@ExperimentalMaterial3Api
private fun NavGraphBuilder.mainNavGraph(
    onMovieSelected: (Int, NavBackStackEntry) -> Unit,
    onTvSelected: (Int, NavBackStackEntry) -> Unit,
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
        val movieId = arguments.getInt(MainDestinations.MOVIE_ID_KEY)
        val viewModel: DetailMovieViewModel = hiltViewModel()
        DetailMovieScreen(movieId, viewModel)
    }
    composable(
        "${MainDestinations.TV_DETAIL_ROUTE}/{${MainDestinations.TV_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.TV_ID_KEY) { type = NavType.IntType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val tvId = arguments.getInt(MainDestinations.TV_ID_KEY)
        val viewModel: DetailTvViewModel = hiltViewModel()
        DetailTvScreen(tvId, viewModel)
    }
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
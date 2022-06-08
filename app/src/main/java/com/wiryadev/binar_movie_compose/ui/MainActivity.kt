package com.wiryadev.binar_movie_compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.ui.theme.BinarMovieComposeTheme
import com.wiryadev.binar_movie_compose.ui.theme.md_theme_light_primary
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUser()
        installSplashScreen()

        setContent {
            val mainUiState: MainUiState by viewModel.uiState.collectAsState()

            BinarMovieComposeTheme(darkTheme = false) {
                MainScreen(state = mainUiState)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    state: MainUiState
) {
    when (state) {
        is MainUiState.Initial -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(md_theme_light_primary),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_round_smart_display_24),
                    contentDescription = null,
                )
            }
        }
        is MainUiState.Loaded -> {
            BinarMovieApp(
                isLoggedIn = state.isLoggedIn
            )
        }
    }
}
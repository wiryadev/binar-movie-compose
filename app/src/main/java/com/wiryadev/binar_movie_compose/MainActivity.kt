package com.wiryadev.binar_movie_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiryadev.binar_movie_compose.ui.BinarMovieApp
import com.wiryadev.binar_movie_compose.ui.MainViewModel
import com.wiryadev.binar_movie_compose.ui.theme.BinarMovieComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            val isLoggedIn by viewModel.isLoggedIn.collectAsState()
            viewModel.getUser()
            BinarMovieComposeTheme {
                BinarMovieApp(isLoggedIn)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BinarMovieComposeTheme {
        Greeting("Android")
    }
}
package com.wiryadev.binar_movie_compose.ui.home.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.data.preference.AuthModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier,
) {
    val userSession by viewModel.userSession.collectAsState(
        initial = AuthModel("", "")
    )
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = userSession) {
        if (userSession.email.isNotEmpty()) {
            viewModel.getUser(email = userSession.email)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = stringResource(id = R.string.profile),
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (uiState.user != null) {
                    stringResource(id = R.string.logged_in_as, uiState.user!!.email)
                } else {
                    "Default User"
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.placeholder(
                    visible = uiState.user == null,
                    highlight = PlaceholderHighlight.shimmer(),
                )
            )
        }

        Button(
            onClick = { viewModel.logout() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .placeholder(
                    visible = uiState.isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                ),
            enabled = !uiState.isLoading,
        ) {
            Text(text = stringResource(id = R.string.logout))
        }
    }
}
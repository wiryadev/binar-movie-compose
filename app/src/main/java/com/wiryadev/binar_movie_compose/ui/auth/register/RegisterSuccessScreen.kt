package com.wiryadev.binar_movie_compose.ui.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.ui.theme.BinarMovieComposeTheme

@Composable
fun RegisterSuccessScreen(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_done
                ),
                contentDescription = stringResource(id = R.string.registration_success),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.registration_success),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(id = R.string.registration_success_subtitle),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp),
        ) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}

@Preview
@Composable
fun RegisterSuccessPreview() {
    BinarMovieComposeTheme {
        RegisterSuccessScreen(onLoginClick = {})
    }
}
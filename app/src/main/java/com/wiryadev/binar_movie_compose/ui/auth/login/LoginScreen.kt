package com.wiryadev.binar_movie_compose.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.ui.components.*

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateRegisterClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.padding(16.dp)
    ) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding,
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.login),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Image(
                        painter = painterResource(id = R.drawable.banner_binar),
                        contentDescription = stringResource(id = R.string.logo),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    val focusRequester = remember { FocusRequester() }
                    val emailState = remember { EmailState() }
                    EmailTextField(emailState, onImeAction = { focusRequester.requestFocus() })

                    Spacer(modifier = Modifier.height(16.dp))

                    val passwordState = remember { PasswordState() }
                    PasswordTextField(
                        label = stringResource(id = R.string.password),
                        passwordState = passwordState,
                        modifier = Modifier.focusRequester(focusRequester),
                        onImeAction = {
                            viewModel.login(emailState.text, passwordState.text)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            viewModel.login(emailState.text, passwordState.text)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .placeholder(
                                visible = uiState.isLoading,
                                highlight = PlaceholderHighlight.shimmer(),
                            ),
                        enabled = emailState.isValid
                                && passwordState.isValid
                                && !uiState.isLoading
                    ) {
                        Text(
                            text = stringResource(id = R.string.login)
                        )
                    }
                }
            }
            item {
                TextButton(
                    onClick = onNavigateRegisterClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.register))
                }
            }
        }
    }
}
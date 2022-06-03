package com.wiryadev.binar_movie_compose.ui.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.ui.components.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


@ExperimentalMaterial3Api
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSubmitted: (username: String, email: String, password: String) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccess) {
        RegisterSuccessScreen(onLoginClick = onNavigateUp)

    } else {
        RegisterFormScreen(
            uiState = uiState,
            onRegisterSubmitted = onRegisterSubmitted,
            modifier = modifier,
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun RegisterFormScreen(
    uiState: RegisterUiState,
    onRegisterSubmitted: (username: String, email: String, password: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // decouple snackbar host state from scaffold state for demo purposes
    // this state, channel and flow is for demo purposes to demonstrate business logic layer
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    // we allow only one snackbar to be in the queue here, hence conflated
    val channel = remember { Channel<String>(Channel.CONFLATED) }
    LaunchedEffect(channel) {
        channel.receiveAsFlow().collect { message ->
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "OK"
            )
        }
    }

    uiState.errorMessage?.let {
        channel.trySend(it)
    }

    Scaffold(
        modifier = modifier.padding(16.dp),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                        text = stringResource(id = R.string.register),
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
                    val passwordFocusRequest = remember { FocusRequester() }
                    val confirmationPasswordFocusRequest = remember { FocusRequester() }

                    val usernameState = remember { TextFieldState() }
                    UsernameTextField(usernameState)

                    Spacer(modifier = Modifier.height(16.dp))
                    val emailState = remember { EmailState() }
                    EmailTextField(
                        emailState,
                        onImeAction = { passwordFocusRequest.requestFocus() })

                    Spacer(modifier = Modifier.height(16.dp))
                    val passwordState = remember { PasswordState() }
                    PasswordTextField(
                        label = stringResource(id = R.string.password),
                        passwordState = passwordState,
                        imeAction = ImeAction.Next,
                        onImeAction = { confirmationPasswordFocusRequest.requestFocus() },
                        modifier = Modifier.focusRequester(passwordFocusRequest)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    val confirmPasswordState =
                        remember { ConfirmPasswordState(passwordState = passwordState) }
                    PasswordTextField(
                        label = stringResource(id = R.string.confirm_password),
                        passwordState = confirmPasswordState,
                        onImeAction = {
                            onRegisterSubmitted(
                                usernameState.text,
                                emailState.text,
                                passwordState.text,
                            )
                        },
                        modifier = Modifier.focusRequester(confirmationPasswordFocusRequest)
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            onRegisterSubmitted(
                                usernameState.text,
                                emailState.text,
                                passwordState.text,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(
                                visible = uiState.isLoading,
                                highlight = PlaceholderHighlight.shimmer(),
                            ),
                        enabled = emailState.isValid
                                && passwordState.isValid
                                && confirmPasswordState.isValid
                                && !uiState.isLoading
                    ) {
                        Text(text = stringResource(id = R.string.register))
                    }
                }
            }
        }
    }
}
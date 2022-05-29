package com.wiryadev.binar_movie_compose.ui.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.wiryadev.binar_movie_compose.R
import com.wiryadev.binar_movie_compose.ui.components.*

@ExperimentalMaterial3Api
@Composable
fun RegisterScreen(
    onRegisterSubmitted: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
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
                    Username(usernameState)

                    Spacer(modifier = Modifier.height(16.dp))
                    val emailState = remember { EmailState() }
                    Email(emailState, onImeAction = { passwordFocusRequest.requestFocus() })

                    Spacer(modifier = Modifier.height(16.dp))
                    val passwordState = remember { PasswordState() }
                    Password(
                        label = stringResource(id = R.string.password),
                        passwordState = passwordState,
                        imeAction = ImeAction.Next,
                        onImeAction = { confirmationPasswordFocusRequest.requestFocus() },
                        modifier = Modifier.focusRequester(passwordFocusRequest)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    val confirmPasswordState =
                        remember { ConfirmPasswordState(passwordState = passwordState) }
                    Password(
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onRegisterSubmitted(
                                usernameState.text,
                                emailState.text,
                                passwordState.text,
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = emailState.isValid &&
                                passwordState.isValid && confirmPasswordState.isValid
                    ) {
                        Text(text = stringResource(id = R.string.register))
                    }
                }
            }
        }
    }
}
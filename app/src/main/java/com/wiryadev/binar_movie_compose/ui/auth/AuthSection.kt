package com.wiryadev.binar_movie_compose.ui.auth

import androidx.annotation.StringRes
import com.wiryadev.binar_movie_compose.R

enum class AuthSections(
    @StringRes val title: Int,
    val route: String
) {
    LOGIN(R.string.login, "auth/login"),
    REGISTER(R.string.login, "auth/register")
}
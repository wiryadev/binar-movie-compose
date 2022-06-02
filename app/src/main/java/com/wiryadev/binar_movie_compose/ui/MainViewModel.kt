package com.wiryadev.binar_movie_compose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.binar_movie_compose.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel()  {

    private val _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            userRepository.getUserSession().collectLatest { user ->
                _isLoggedIn.value = user.email.isNotBlank()
            }
        }
    }

}
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
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> =
        MutableStateFlow(MainUiState.Initial)
    val uiState: StateFlow<MainUiState> get() = _uiState.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            userRepository.getUserSession().collectLatest { user ->
                _uiState.value = MainUiState.Loaded(
                    isLoggedIn = user.email.isNotBlank()
                )
            }
        }
    }

}

sealed class MainUiState {
    object Initial : MainUiState()
    data class Loaded(val isLoggedIn: Boolean) : MainUiState()
}
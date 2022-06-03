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

    private val _mainUiState: MutableStateFlow<MainUiState> =
        MutableStateFlow(MainUiState.Initial)
    val mainUiState: StateFlow<MainUiState> get() = _mainUiState.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            userRepository.getUserSession().collectLatest { user ->
                _mainUiState.value = MainUiState.Loaded(
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
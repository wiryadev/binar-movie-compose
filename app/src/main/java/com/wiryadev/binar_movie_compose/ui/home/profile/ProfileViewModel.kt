package com.wiryadev.binar_movie_compose.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.binar_movie_compose.data.local.entity.UserEntity
import com.wiryadev.binar_movie_compose.data.preference.AuthModel
import com.wiryadev.binar_movie_compose.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val userSession: Flow<AuthModel> get() = userRepository.getUserSession()

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> get() = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            userRepository.deleteUserSession()
        }
    }

    fun getUser(email: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            userRepository.getUser(email = email).collectLatest { user ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = user,
                    )
                }
            }
        }
    }

}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: UserEntity? = null,
    val result: Int = 0,
    val errorMessage: String? = null,
)
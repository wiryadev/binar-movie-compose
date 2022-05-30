package com.wiryadev.binar_movie_compose.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.binar_movie_compose.data.local.entity.UserEntity
import com.wiryadev.binar_movie_compose.data.preference.AuthModel
import com.wiryadev.binar_movie_compose.data.remote.Result
import com.wiryadev.binar_movie_compose.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    //    private val _userSession: MutableStateFlow<AuthModel> = MutableStateFlow()
    val userSession: Flow<AuthModel> get() = userRepository.getUserSession()

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> get() = _uiState.asStateFlow()

//    init {
//        checkUserSession()
//    }
//
//    private fun checkUserSession() {
//        viewModelScope.launch {
//            userRepository.getUserSession().collect {
//                _userSession.value = it
//            }
//        }
//    }

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

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            val result = userRepository.updateUser(user = user)
            _uiState.update {
                when (result) {
                    is Result.Success -> it.copy(
                        isLoading = false,
                        result = result.data,
                    )
                    is Result.Error -> it.copy(
                        isLoading = false,
                        errorMessage = result.exception.message
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
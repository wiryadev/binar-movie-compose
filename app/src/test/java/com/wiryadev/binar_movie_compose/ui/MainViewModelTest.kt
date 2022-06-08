package com.wiryadev.binar_movie_compose.ui

import app.cash.turbine.test
import com.wiryadev.binar_movie_compose.TestDispatcherRule
import com.wiryadev.binar_movie_compose.data.preference.AuthModel
import com.wiryadev.binar_movie_compose.data.repositories.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule(UnconfinedTestDispatcher())

    private val repository: UserRepository = mockk()
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `when uiState is initialized, then UI State is loading`() = runTest {
        viewModel.uiState.test {
            assertTrue(awaitItem() is MainUiState.Initial)
        }
    }

    @Test
    fun `when user logged in, should return True`() = runTest {
        every {
            repository.getUserSession()
        } returns flowOf(AuthModel("username", "email"))

        viewModel.getUser()
        val actual = viewModel.uiState.first()

        assertTrue(actual is MainUiState.Loaded)
        if (actual is MainUiState.Loaded) {
            assertEquals(true, actual.isLoggedIn)
        }
    }

    @Test
    fun `when user is not logged in, should return False`() = runTest {
        every {
            repository.getUserSession()
        } returns flowOf(AuthModel("", ""))

        viewModel.getUser()
        val actual = viewModel.uiState.first()

        assertTrue(actual is MainUiState.Loaded)
        if (actual is MainUiState.Loaded) {
            assertEquals(false, actual.isLoggedIn)
        }
    }

}
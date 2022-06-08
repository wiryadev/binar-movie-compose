package com.wiryadev.binar_movie_compose.ui.home.movie.detail

import app.cash.turbine.test
import com.wiryadev.binar_movie_compose.MovieDataDummy
import com.wiryadev.binar_movie_compose.TestDispatcherRule
import com.wiryadev.binar_movie_compose.data.repositories.movie.FakeMovieRepository
import com.wiryadev.binar_movie_compose.data.repositories.movie.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailMovieViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule(UnconfinedTestDispatcher())

    private val repository: MovieRepository = FakeMovieRepository()
    private lateinit var viewModel: DetailMovieViewModel

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(repository)
    }

    @Test
    fun `when uiState is initialized, then Loading is false`() = runTest {
        viewModel.uiState.test {
            assertEquals(false, awaitItem().isLoading)
        }
    }

    @Test
    fun `when uiState is initialized, then errorMessage is null`() = runTest {
        viewModel.uiState.test {
            assertEquals(null, awaitItem().errorMessage)
        }
    }

    @Test
    fun `when uiState is initialized, then Movie Detail is null`() = runTest {
        viewModel.uiState.test {
            assertEquals(null, awaitItem().movie)
        }
    }

    @Test
    fun `when Get Detail Movie By Id, should return Success`() = runTest {
        viewModel.getDetail(1)
        val actual = viewModel.uiState.first()

        assertEquals(MovieDataDummy.generateDetailMovieResponse(1), actual.movie)
    }

    @Test
    fun `when Get Detail Movie By Id, should return Error`() = runTest {
        viewModel.getDetail(999)
        val actual = viewModel.uiState.first()

        assertEquals("Movie Not Found", actual.errorMessage)
    }

}
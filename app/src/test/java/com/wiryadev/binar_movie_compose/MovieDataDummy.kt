package com.wiryadev.binar_movie_compose

import com.wiryadev.binar_movie_compose.data.remote.movie.dto.DetailMovieResponse
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.ListMovieResponse
import com.wiryadev.binar_movie_compose.data.remote.movie.dto.MovieDto

object MovieDataDummy {

    val listMovieResponse = ListMovieResponse(
        page = 1,
        movies = generateMovieDtoList(),
        totalPages = 30,
        totalResults = 30
    )

    fun generateDynamicMovieList(n: Int) = ListMovieResponse(
        page = 1,
        movies = generateMovieDtoList(n),
        totalPages = n,
        totalResults = n
    )

    private fun generateMovieDtoList(n: Int = 30): List<MovieDto> {
        val list = mutableListOf<MovieDto>()
        for (i in 1..n) {
            list.add(
                MovieDto(
                    adult = false,
                    backdropPath = "/backdropMovie$i",
                    genreIds = listOf(),
                    id = i,
                    originalLanguage = "",
                    originalTitle = "",
                    overview = "",
                    popularity = 0.0,
                    posterPath = "/posterMovie$i",
                    releaseDate = "",
                    title = "Title $i",
                    video = false,
                    voteAverage = 0.0,
                    voteCount = 0
                )
            )
        }
        return list
    }

    val detailMovie = generateDetailMovieResponse()

    fun generateDetailMovieResponse(i: Int = 1) = DetailMovieResponse(
        adult = false,
        backdropPath = "/detailMovie$i",
        budget = i * 100000,
        genres = listOf(),
        homepage = "",
        id = i,
        imdbId = "",
        originalLanguage = "",
        originalTitle = "",
        overview = "",
        popularity = 0.0,
        posterPath = "",
        releaseDate = "",
        revenue = 0,
        runtime = 0,
        status = "",
        tagline = "",
        title = "Title $i",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )

}
package com.son.movie.repository

import com.son.movie.model.BookmarkRequest
import com.son.movie.model.BookmarkResponse
import com.son.movie.model.Genre
import com.son.movie.model.Movie
import com.son.movie.model.Movies
import com.son.movie.model.Trailer
import com.son.movie.model.Trailers

class FakeMovieRepository() : MovieRepository {

    private

    val fakeBookmarkRequest = BookmarkRequest(
        mediaId = 123,
        mediaType = "movie",
        watchlist = true
    )

    val fakeBookmarkResponse = BookmarkResponse(
        success = true,
        statusCode = 1,
        statusMessage = "Bookmark added successfully"
    )

    val fakeGenre = Genre(
        id = 1,
        name = "Action"
    )

    val fakeMovie = Movie(
        id = 1234,
        title = "Sample Movie",
        posterPath = "/sample_poster.jpg",
        releaseDate = "2023-09-13",
        backdropPath = "/sample_backdrop.jpg",
        overview = "This is a sample movie overview.",
        runtime = 120,
        genres = listOf(fakeGenre),
        voteAverage = 7.5f
    )

    val fakeMovie1 = Movie(
        id = 5678,
        title = "Another Sample Movie 1",
        posterPath = "/sample_poster_1.jpg",
        releaseDate = "2023-09-14",
        backdropPath = "/sample_backdrop_1.jpg",
        overview = "This is another sample movie overview 1.",
        runtime = 110,
        genres = listOf(fakeGenre),
        voteAverage = 8.0f
    )

    val fakeMovie2 = Movie(
        id = 9101,
        title = "Another Sample Movie 2",
        posterPath = "/sample_poster_2.jpg",
        releaseDate = "2023-09-15",
        backdropPath = "/sample_backdrop_2.jpg",
        overview = "This is another sample movie overview 2.",
        runtime = 105,
        genres = listOf(fakeGenre),
        voteAverage = 7.2f
    )

    val fakeMovies = Movies(
        page = 1,
        results = listOf(fakeMovie, fakeMovie1, fakeMovie2)
    )

    val fakeTrailer = Trailer(
        type = "Trailer",
        key = "sample_trailer_key"
    )

    val fakeTrailers = Trailers(
        resultVideoMovie = listOf(fakeTrailer)
    )

    override suspend fun getTrendingMovieTodayAsync(): Movies {
        return fakeMovies
    }

    override suspend fun getMovieDetailsAsync(movieId: Int): Movie {
        return fakeMovie
    }

    override suspend fun getMovieVideosAsync(movieId: Int): Trailers {
        return fakeTrailers
    }

    override suspend fun getResultSearchAsync(query: String): Movies {
        return fakeMovies
    }

    override suspend fun getNowPlayingMoviesAsync(): Movies {
        return fakeMovies
    }

    override suspend fun getUpcomingMoviesAsync(): Movies {
        return fakeMovies
    }

    override suspend fun getTopRatedMoviesAsync(): Movies {
        return fakeMovies
    }

    override suspend fun getPopularMoviesAsync(): Movies {
        return fakeMovies
    }

    override suspend fun getWatchlistMoviesAsync(): Movies {
        return fakeMovies
    }

    override suspend fun addToWatchlistAsync(bookmarkRequest: BookmarkRequest): BookmarkResponse {
        return fakeBookmarkResponse
    }
}
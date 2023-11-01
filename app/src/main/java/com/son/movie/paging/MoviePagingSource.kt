package com.son.movie.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.son.movie.model.Movie
import com.son.movie.network.MovieApiService
import retrofit2.HttpException

const val STARTING_KEY = 1

class MoviePagingSource(private val movieApi: MovieApiService, private val query: String) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_KEY

        return try {
            val response = movieApi.getResultSearchAsync(query = query, page = position)

            val repos = response.await()

            LoadResult.Page(
                data = repos.results,
                prevKey = if (position == STARTING_KEY) null else position - 1,
                nextKey = if (position == repos.totalPage) null else position + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
package com.example.pagingthreenew.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingthreenew.data.api.MovieApiService
import com.example.pagingthreenew.models.Movie
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val apiService: MovieApiService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getPopularMovies(page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }
}
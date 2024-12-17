package com.example.pagingthreenew.data.api

import com.example.pagingthreenew.models.MovieResponse
import com.example.pagingthreenew.utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieResponse
}
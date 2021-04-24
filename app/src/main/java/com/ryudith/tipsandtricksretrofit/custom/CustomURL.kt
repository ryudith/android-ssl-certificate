package com.ryudith.tipsandtricksretrofit.custom

import retrofit2.Response
import retrofit2.http.GET

interface CustomURL {
    @GET("custom_retrofit.php")
    suspend fun getMethod() : Response<ServerResponse>

    @GET("custom_retrofit.php")
    suspend fun getMoviesResponse() : Response<MovieResponse>

    @GET("custom_retrofit.php")
    suspend fun getMoviesList() : Response<Movie>
}
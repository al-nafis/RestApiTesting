package com.mnafis.restapitesting.omdb

import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface OmdbService {
    @POST("/")
    fun getMovieInfo(
        @Query("apikey") key: String,
        @Query("t") title: String,
        @Query("type") type: String
    ): Single<MovieResponse>

    @POST("/")
    fun getMovieList(
        @Query("apikey") key: String,
        @Query("s") searchKey: String,
        @Query("type") type: String
    ): Single<MovieListResponse>
}
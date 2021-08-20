package com.mnafis.restapitesting.omdb

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface OmdbService {
    @POST("/")
    fun getMovieInfoRx(
        @Query("apikey") key: String,
        @Query("t") title: String,
        @Query("type") type: String
    ): Single<MovieResponse>

    @POST("/")
    fun getMovieListRx(
        @Query("apikey") key: String,
        @Query("s") searchKey: String,
        @Query("type") type: String
    ): Single<MovieListResponse>

    @POST("/")
    suspend fun getMovieInfoCoroutine(
        @Query("apikey") key: String,
        @Query("t") title: String,
        @Query("type") type: String
    ): Response<MovieResponse>

    @POST("/")
    suspend fun getMovieListCoroutine(
        @Query("apikey") key: String,
        @Query("s") searchKey: String,
        @Query("type") type: String
    ): Response<MovieListResponse>
}
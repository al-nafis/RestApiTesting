package com.mnafis.restapitesting.omdb

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class OmdbManager {

    private val rxService = Retrofit.Builder()
        .baseUrl("https://omdbapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(OmdbService::class.java)

    fun getMovieDetailsRx(name: String): Single<MovieResponse> {
        return rxService.getMovieInfoRx("34737eb2", name, "movie").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMovieListRx(searchKey: String): Single<MovieListResponse> {
        return rxService.getMovieListRx("34737eb2", searchKey, "movie").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private val coroutineService = Retrofit.Builder()
        .baseUrl("https://omdbapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OmdbService::class.java)

    suspend fun getMovieDetailsCoroutine(name: String): Response<MovieResponse> =
            rxService.getMovieInfoCoroutine ("34737eb2", name, "movie")

    suspend fun getMovieListCoroutine(searchKey: String): Response<MovieListResponse> {
        return rxService.getMovieListCoroutine("34737eb2", searchKey, "movie")
    }
}
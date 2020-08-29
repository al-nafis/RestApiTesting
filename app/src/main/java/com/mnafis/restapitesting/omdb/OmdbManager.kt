package com.mnafis.restapitesting.omdb

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class OmdbManager {

    private val service = Retrofit.Builder()
        .baseUrl("https://omdbapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(OmdbService::class.java)

    fun getMovieDetails(name: String): Single<MovieResponse> {
        return service.getMovieInfo("34737eb2", name, "movie").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMovieList(searchKey: String): Single<MovieListResponse> {
        return service.getMovieList("34737eb2", searchKey, "movie").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun <T> Single<T>.getLoadedSingle(): Single<T> {
        return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun <T> Observable<T>.getLoadedSingle(): Observable<T> {
        return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
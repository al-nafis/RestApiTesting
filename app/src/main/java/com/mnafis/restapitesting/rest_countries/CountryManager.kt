package com.mnafis.restapitesting.rest_countries

import com.mnafis.restapitesting.omdb.OmdbService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryManager {
    private val service = Retrofit.Builder()
        .baseUrl("https://restcountries.eu/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CountryService::class.java)

    fun getCountryByName(name: String) : Call<List<CountryResponse>> =
        service.getCountryByName(name)
}
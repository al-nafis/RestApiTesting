package com.mnafis.restapitesting.rest_countries

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {
    @GET("/rest/v2/name/{name}")
    fun getCountryByName(@Path("name") name: String) : Call<List<CountryResponse>>
}
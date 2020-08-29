package com.mnafis.restapitesting.rest_countries

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("capital")
    val capital: String,
    @SerializedName("languages")
    val languages: List<Language>
)

data class Language(
    @SerializedName("name")
    val name: String
)
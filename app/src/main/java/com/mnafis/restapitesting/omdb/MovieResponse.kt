package com.mnafis.restapitesting.omdb

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Title")
    val movieTitle: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Poster")
    val poster: String
)
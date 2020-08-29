package com.mnafis.restapitesting.omdb

import com.google.gson.annotations.SerializedName


data class MovieListResponse (
    @SerializedName("Search")
    val movieList: List<MovieResponse>
)
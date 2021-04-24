package com.ryudith.tipsandtricksretrofit.custom

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("movie_id")
    val id : Int,
    val name : String,
    val year : Int
)

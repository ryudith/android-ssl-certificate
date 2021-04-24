package com.ryudith.tipsandtricksretrofit.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.ryudith.tipsandtricksretrofit.custom.Movie
import com.ryudith.tipsandtricksretrofit.custom.MovieResponse
import java.lang.reflect.Type

class MovieListConverter : JsonDeserializer<List<Movie>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Movie> {
        val resp = mutableListOf<Movie>()

        val content = json?.asJsonObject?.get("data")
        if (content != null) {
            for (item in content!!.asJsonArray) {
                val tmp = item.asJsonObject
                resp.add(Movie(tmp.get("movie_id").asInt, tmp.get("name").asString, tmp.get("year").asInt))
            }
        }

        return resp
    }
}
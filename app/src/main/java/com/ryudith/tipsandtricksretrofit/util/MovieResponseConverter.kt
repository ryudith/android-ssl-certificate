package com.ryudith.tipsandtricksretrofit.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.ryudith.tipsandtricksretrofit.custom.Movie
import com.ryudith.tipsandtricksretrofit.custom.MovieResponse
import java.lang.reflect.Type

class MovieResponseConverter : JsonDeserializer<MovieResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MovieResponse {
        val resp = MovieResponse()

        val content = json?.asJsonObject?.get("data")
        if (content != null) {
            for (item in content!!.asJsonArray) {
                val tmp = item.asJsonObject
                resp.movies.add(Movie(tmp.get("movie_id").asInt, tmp.get("name").asString, tmp.get("year").asInt))
            }
        }

        return resp
    }
}
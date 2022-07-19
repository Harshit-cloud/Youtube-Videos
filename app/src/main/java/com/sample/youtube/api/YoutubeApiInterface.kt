package com.sample.youtube.api

import com.sample.youtube.model.CommonResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface YoutubeApiInterface {

    companion object {
        const val BASE_URL = "https://youtube.googleapis.com/youtube/"
        var token=""
    }

    @GET("v3/videos")
    suspend fun getVideos(
        @Query("part") part: String,
        @Query("chart") chart: String?,
        @Query("key") key: String,
        @Query("pageToken") pageToken: String=""
    ): CommonResponse

}

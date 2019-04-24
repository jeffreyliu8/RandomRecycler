package com.askjeffreyliu.testRecycler.endpoint

import com.askjeffreyliu.testRecycler.model.QueryResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsWebService {
    @GET("/v2/everything")
    fun getNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("sortBy") sortBy: String?,
        @Query("apiKey") apiKey: String?
    ): Call<QueryResult>
}
package com.askjeffreyliu.testRecycler.model


data class QueryResult(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>? = null
)
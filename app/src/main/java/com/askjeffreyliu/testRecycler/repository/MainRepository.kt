package com.askjeffreyliu.testRecycler.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData


import com.askjeffreyliu.testRecycler.MyApplication
import com.askjeffreyliu.testRecycler.endpoint.NewsWebService
import com.askjeffreyliu.testRecycler.model.Article
import com.askjeffreyliu.testRecycler.model.QueryResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainRepository(application: Application) {
    @Inject
    lateinit var webService: NewsWebService

    init {
        val app = application as MyApplication
        app.component.inject(this)
    }

    fun getNews(date: String, liveData: MutableLiveData<List<Article>>) {
        webService.getNews("ai", date, date, "publishedAt", "d1d760ed1c1e4b5189e8b810108ac762")
            .enqueue(object : Callback<QueryResult> {
                override fun onFailure(call: Call<QueryResult>, t: Throwable) {
                    println(t.localizedMessage)
                }

                override fun onResponse(call: Call<QueryResult>, response: Response<QueryResult>) {
                    if (response.isSuccessful) {
                        liveData.value = response.body()?.articles
                    } else {
                        println("Error code: " + response.code())
                    }
                }
            })
    }
}
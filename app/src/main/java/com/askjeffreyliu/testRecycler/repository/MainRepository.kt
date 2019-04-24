package com.askjeffreyliu.testRecycler.repository

import android.app.Application
import androidx.lifecycle.LiveData


import com.askjeffreyliu.testRecycler.MyApplication
import com.askjeffreyliu.testRecycler.endpoint.NewsWebService
import com.askjeffreyliu.testRecycler.extension.formatToInt
import com.askjeffreyliu.testRecycler.extension.formatToString
import com.askjeffreyliu.testRecycler.model.Article
import com.askjeffreyliu.testRecycler.model.QueryResult
import com.askjeffreyliu.testRecycler.room.ArticleDb
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class MainRepository(application: Application) {
    @Inject
    lateinit var webService: NewsWebService

    private val dao = ArticleDb.getDatabase(application).articleDao()

    init {
        val app = application as MyApplication
        app.component.inject(this)
    }

    fun getNewsFromDb(): LiveData<List<Article>> {
        return dao.getAll()
    }

    fun getNews(date: Date) {
        webService.getNews(
            "ai",
            date.formatToString(),
            date.formatToString(),
            "publishedAt",
            "d1d760ed1c1e4b5189e8b810108ac762"
        )
            .enqueue(object : Callback<QueryResult> {
                override fun onFailure(call: Call<QueryResult>, t: Throwable) {
                    println(t.localizedMessage)
                }

                override fun onResponse(call: Call<QueryResult>, response: Response<QueryResult>) {
                    if (response.isSuccessful) {
                        response.body()?.articles?.let {
                            doAsync {
                                for (article in it) {
                                    article.date = date.formatToInt()
                                }
                                dao.insertAll(it)
                            }
                        }
                    } else {
                        println("Error code: " + response.code())
                    }
                }
            })
    }
}
package com.askjeffreyliu.testRecycler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.askjeffreyliu.testRecycler.model.Article


import com.askjeffreyliu.testRecycler.repository.MainRepository
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainRepository(application)

    private val liveData = MutableLiveData<List<Article>>()

    fun getNews() {
        val cDate = Date()
        val fDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cDate)
        repository.getNews(fDate, liveData)
    }

    fun getLiveData() = liveData
}

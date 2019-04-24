package com.askjeffreyliu.testRecycler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.askjeffreyliu.testRecycler.model.Article


import com.askjeffreyliu.testRecycler.repository.MainRepository


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainRepository(application)

    private val liveData = MutableLiveData<List<Article>>()

    fun getNews() {
        repository.getNews("2019-04-23", liveData)
    }

    fun getLiveData() = liveData
}

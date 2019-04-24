package com.askjeffreyliu.testRecycler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.askjeffreyliu.testRecycler.extension.formatToDate
import com.askjeffreyliu.testRecycler.extension.formatToInt
import com.askjeffreyliu.testRecycler.model.Article


import com.askjeffreyliu.testRecycler.repository.MainRepository
import java.util.*


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainRepository(application)

    private var oldestDate: Int? = null // we only care ymd
    private var newestDate: Int? = null // we only care ymd

    fun getNewsFromDb(): LiveData<List<Article>> {
        return repository.getNewsFromDb()
    }

    /**
     * When user pull down to refresh
     */
    fun refresh() {
        if (Calendar.getInstance().time.formatToInt() == newestDate) {
            // it's same day, just fetch today's news
            repository.getNews(Calendar.getInstance().time)
        } else {
            // it's probably been a few days now, reload
            // or, the user manually change the date? Flying?
            load()
        }
    }

    /**
     * When user first open this activity
     */
    fun load() {
        val date = Calendar.getInstance().time
        newestDate = date.formatToInt()
        oldestDate = date.formatToInt()
        repository.getNews(date)
    }

    fun loadOneMoreDay() {
        val calendar = Calendar.getInstance()
        calendar.time = oldestDate!!.formatToDate()
        calendar.add(Calendar.DATE, -1)
        oldestDate = calendar.time.formatToInt()

        repository.getNews(calendar.time)
    }
}

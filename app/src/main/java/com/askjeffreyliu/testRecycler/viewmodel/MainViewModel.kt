package com.askjeffreyliu.testRecycler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.askjeffreyliu.testRecycler.extension.formatToDate
import com.askjeffreyliu.testRecycler.extension.formatToInt
import com.askjeffreyliu.testRecycler.model.Article


import com.askjeffreyliu.testRecycler.repository.MainRepository
import java.util.*


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainRepository(application)

    // pair of oldest date and newest date
    private var range = MutableLiveData<Pair<Int, Int>>() // we only care ymd, example 20190430
    private var dbLiveData: LiveData<List<Article>>? = null

    private val liveDate = MediatorLiveData<List<Article>>()

    init {
        load()

        liveDate.addSource(range) { pair ->
            dbLiveData?.let {
                liveDate.removeSource(it)
            }
            dbLiveData = repository.getNewsInDates(pair.first, pair.second)
            liveDate.addSource(dbLiveData!!) {
                liveDate.value = it
            }
        }
    }

    fun getLiveData() = liveDate

    /**
     * When user pull down to refresh
     */
    fun refresh() {
        if (Calendar.getInstance().time.formatToInt() == range.value!!.second) {
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
    private fun load() {
        val date = Calendar.getInstance().time

        // get the news for today
        repository.getNews(date)

        // set the range to just today
        range.value = Pair(date.formatToInt(), date.formatToInt())
    }

    fun loadOneMoreDay() {
        // get today
        val calendar = Calendar.getInstance()
        calendar.time = range.value!!.first.formatToDate()

        // extend range by 1 day
        calendar.add(Calendar.DATE, -1)

        // get news of the extended date
        repository.getNews(calendar.time)

        // set the range for the liveData
        range.value = Pair(calendar.time.formatToInt(), range.value!!.second)
    }
}

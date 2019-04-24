package com.askjeffreyliu.testRecycler

import android.app.Application
import com.askjeffreyliu.testRecycler.dependency.DaggerNewsApiComponent
import com.askjeffreyliu.testRecycler.dependency.NewsApiComponent
import com.askjeffreyliu.testRecycler.dependency.NewsWebModule

class MyApplication : Application() {

    lateinit var component: NewsApiComponent

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        component = DaggerNewsApiComponent.builder()
            .newsWebModule(NewsWebModule())
            .build()
    }
}
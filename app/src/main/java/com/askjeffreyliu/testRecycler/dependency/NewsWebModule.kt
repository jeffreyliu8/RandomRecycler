package com.askjeffreyliu.testRecycler.dependency

import com.askjeffreyliu.testRecycler.BuildConfig
import com.askjeffreyliu.testRecycler.endpoint.NewsWebService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NewsWebModule {
    @Provides
    fun provideFBWebService(): NewsWebService {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            // add your other interceptors …
            // add logging as last interceptor
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)  // <-- this is the important line!
        }

        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(NewsWebService::class.java)
    }
}
package com.askjeffreyliu.testRecycler.dependency

import com.askjeffreyliu.testRecycler.repository.MainRepository
import dagger.Component

@Component(modules = [NewsWebModule::class])
interface NewsApiComponent {
    fun inject(repository: MainRepository)
}
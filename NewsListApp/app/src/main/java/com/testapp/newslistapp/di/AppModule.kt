package com.testapp.newslistapp.di

import android.content.Context
import com.testapp.newslistapp.NewsListApp
import dagger.Module
import dagger.Provides

@Module(includes = [NewsListActivityModule::class])
class AppModule {

    @Provides
    fun providesContext(application: NewsListApp): Context {
        return application.applicationContext
    }

}
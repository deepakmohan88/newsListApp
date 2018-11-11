package com.testapp.newslistapp.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.testapp.newslistapp.NewsListApp
import com.testapp.newslistapp.service.NewsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NewsListActivityModule::class])
class AppModule {

    @Provides
    fun providesContext(application: NewsListApp): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideRecipeService(): NewsService {
        return Retrofit.Builder()
                .baseUrl("https://dl.dropboxusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsService::class.java)
    }

}
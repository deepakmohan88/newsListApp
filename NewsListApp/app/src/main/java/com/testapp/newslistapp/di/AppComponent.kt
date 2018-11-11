package com.testapp.newslistapp.di

import com.testapp.newslistapp.NewsListApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, NewsListActivityModule::class])
interface AppComponent : AndroidInjector<NewsListApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<NewsListApp>()
}
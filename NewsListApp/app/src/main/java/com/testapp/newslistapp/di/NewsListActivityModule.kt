package com.testapp.newslistapp.di

import com.testapp.newslistapp.AppSchedulers
import com.testapp.newslistapp.repository.NewsRepository
import com.testapp.newslistapp.ui.NewsListFragment
import com.testapp.newslistapp.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class NewsListActivityModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun providesMainViewModelFactory(newRepository: NewsRepository, schedulers: AppSchedulers)
                : MainViewModelFactory {
            return MainViewModelFactory(newRepository, schedulers)
        }
    }

    @ContributesAndroidInjector()
    internal abstract fun newsListFragment(): NewsListFragment

}
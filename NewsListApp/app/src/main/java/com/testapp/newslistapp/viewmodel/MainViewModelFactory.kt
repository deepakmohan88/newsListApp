package com.testapp.newslistapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.testapp.newslistapp.AppSchedulers
import com.testapp.newslistapp.repository.NewsRepository

class MainViewModelFactory(private val repository: NewsRepository, private var schedulers: AppSchedulers) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            return NewsListViewModel(repository, schedulers) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
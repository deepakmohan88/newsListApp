package com.testapp.newslistapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.testapp.newslistapp.repository.NewsRepository

class MainViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            return NewsListViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
package com.testapp.newslistapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.testapp.newslistapp.data.NewsDetail

class NewsListViewModel : ViewModel() {
    private val _newsList = MutableLiveData<List<NewsDetail>>()

    val isLoading = ObservableBoolean(false)

    val newsList: LiveData<List<NewsDetail>>
        get() = _newsList


    fun loadNewsList() {
        isLoading.set(true)
        var detail = NewsDetail("Beavers",
                "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg")
        _newsList.value = listOf(detail, detail, detail)
        isLoading.set(false)
    }

}
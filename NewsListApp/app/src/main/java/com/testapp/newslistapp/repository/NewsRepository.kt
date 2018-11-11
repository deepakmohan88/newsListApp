package com.testapp.newslistapp.repository

import com.testapp.newslistapp.data.NewsDetail
import io.reactivex.Observable
import javax.inject.Inject

class NewsRepository @Inject constructor() {

    fun getNews(): Observable<List<NewsDetail>> {
        //TODO: Replace with actual data from service
        var newsDetail = NewsDetail("Beavers",
                "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg")
        var newsList = listOf(newsDetail, newsDetail, newsDetail)
        return Observable.just(newsList)
    }
}
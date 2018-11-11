package com.testapp.newslistapp.repository

import com.testapp.newslistapp.data.NewsDetail
import com.testapp.newslistapp.service.NewsResponse
import io.reactivex.Observable
import javax.inject.Inject

class NewsRepository @Inject constructor(var remoteDataSource: NewsRemoteDataSource) {

    fun getNews(): Observable<NewsResponse> {
        return remoteDataSource.getNews()
    }
}
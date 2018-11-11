package com.testapp.newslistapp.repository

import com.testapp.newslistapp.service.NewsResponse
import com.testapp.newslistapp.service.NewsService
import io.reactivex.Observable
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(val recipeService: NewsService) {

    fun getNews(): Observable<NewsResponse> {
        return recipeService.getNews()
    }
}
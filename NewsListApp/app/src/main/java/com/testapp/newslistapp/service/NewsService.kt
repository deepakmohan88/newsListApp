package com.testapp.newslistapp.service

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Retrofit service interface used to fetch data from the REST service
 */
interface NewsService {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getNews(): Observable<NewsResponse>

}
package com.testapp.newslistapp.repository

import com.testapp.newslistapp.service.NewsResponse
import com.testapp.newslistapp.util.NetManager
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository which provides data stream to the ViewModel
 */

@Singleton
class NewsRepository @Inject constructor(var netManager: NetManager, var remoteDataSource: NewsRemoteDataSource) {

    fun getNews(): Observable<NewsResponse> {
        return if (netManager?.isConnectedToInternet == true) {
            remoteDataSource.getNews()
        } else {
            Observable.error(Throwable())
        }
    }
}
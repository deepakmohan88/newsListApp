package com.testapp.newslistapp.repository

import com.testapp.newslistapp.data.Resource
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

    fun getNews(): Observable<Resource<NewsResponse>> {
        netManager.isConnectedToInternet?.let {
            if (it) {
                return remoteDataSource.getNews().map {
                    Resource.success(it)
                }
            }
        }
        return Observable.just(Resource.error(null))
    }
}
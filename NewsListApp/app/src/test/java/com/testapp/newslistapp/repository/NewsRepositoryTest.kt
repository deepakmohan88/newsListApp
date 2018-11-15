package com.testapp.newslistapp.repository

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.testapp.newslistapp.service.NewsResponse
import com.testapp.newslistapp.util.NetManager
import io.reactivex.Observable
import org.junit.Test

class NewsRepositoryTest {

    private val newsRemoteDataSource = mock<NewsRemoteDataSource>()
    private val netManager = mock<NetManager>()
    private val newsRepository = NewsRepository(netManager, newsRemoteDataSource)

    @Test
    fun testGetNewsWhenNetworkAvailable() {
        whenever(netManager.isConnectedToInternet).thenReturn(true)
        whenever(newsRemoteDataSource.getNews()).thenReturn(Observable.just(mock<NewsResponse>()))
        val newsObserver = newsRepository.getNews()

        verify(newsRemoteDataSource).getNews()
        newsObserver.test().assertNoErrors()
    }

    @Test
    fun testGetNewsWhenNetworkUnavailable() {
        whenever(netManager.isConnectedToInternet).thenReturn(false)
        val newsObserver = newsRepository.getNews()

        newsObserver.test().assertFailure(Throwable::class.java)
    }
}
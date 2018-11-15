package com.testapp.newslistapp.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

/**
 * Helper class to check the network connectivity status
 */
class NetManager @Inject constructor(var applicationContext: Context) {

    val isConnectedToInternet: Boolean?
        get() {
            val conManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = conManager.activeNetworkInfo
            return ni != null && ni.isConnected
        }
}

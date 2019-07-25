package com.example.hmn.utils.network

import java.io.IOException
import javax.inject.Singleton

/*
Helper class for network related functions
 */
@Singleton
class NetworkHelper {

    /*
    Function to check network connection
     */
    @Throws(InterruptedException::class, IOException::class)
    fun isNetworkConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }

}
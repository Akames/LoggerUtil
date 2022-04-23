package com.akame.logger.interceptor

import android.util.Log
import com.akame.logger.Chain

internal class PrintInterceptor : ILogInterceptor {

    override fun log(priority: Int, tag: String, logMsg: String, chain: Chain) {
        Log.println(priority, tag, logMsg)
    }
}
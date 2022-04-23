package com.akame.loggerutil

import com.akame.logger.Chain
import com.akame.logger.interceptor.ILogInterceptor

class MyInterceptor : ILogInterceptor {
    override fun log(priority: Int, tag: String, logMsg: String, chain: Chain) {
        chain.proceed(priority, tag, logMsg)
    }
}
package com.akame.logger.interceptor

import com.akame.logger.Chain

interface ILogInterceptor {
    fun log(priority: Int, tag: String, logMsg: String, chain: Chain)
}
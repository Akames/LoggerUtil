package com.akame.logger

import com.akame.logger.interceptor.ILogInterceptor

class Chain(private val interceptors: List<ILogInterceptor>, private val index: Int = 0) {

    fun proceed(priority: Int, tag: String, logMsg: String) {
        val next = Chain(interceptors, index + 1)
        interceptors.getOrNull(index)?.log(priority, tag, logMsg, next)
    }
}
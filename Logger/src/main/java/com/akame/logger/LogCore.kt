package com.akame.logger

import com.akame.logger.interceptor.CallStackInterceptor
import com.akame.logger.interceptor.ILogInterceptor
import com.akame.logger.interceptor.IOInterceptor
import com.akame.logger.interceptor.PrintInterceptor

internal class LogCore(private val config: LogConfig) {
    private val chain: Chain

    init {
        val interceptors = arrayListOf<ILogInterceptor>()
        config.interceptors?.let { interceptors.addAll(it) }
        if (config.isSaveLocal) {
            interceptors.add(IOInterceptor(config.localPath))
        }
        interceptors.add(CallStackInterceptor(config.printClassNameTag, config.packageName))
        interceptors.add(PrintInterceptor())
        chain = Chain(interceptors)
    }

    fun log(priority: Int, msg: String) {
        if (!config.isDebug) {
            return
        }
        chain.proceed(priority, config.logTag, msg)
    }
}
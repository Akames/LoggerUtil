package com.akame.logger

import com.akame.logger.interceptor.ILogInterceptor
import java.lang.RuntimeException

class LogConfig(builder: Builder) {
    var isDebug = builder.isDebug
    var logTag = builder.logTag
    var printClassNameTag = builder.printClassNameTag
    var packageName = builder.packageName
    val interceptors = builder.interceptors
    var isSaveLocal = builder.isSaveLocal
    var localPath = builder.localPath

    class Builder {
        //是否输出日志
        var isDebug = true

        //日志tag
        var logTag = ""

        //是否以类名输出tag
        var printClassNameTag = false

        //应用项目包名 用于过滤堆栈信息
        var packageName: String = ""

        //拦截器
        var interceptors: ArrayList<ILogInterceptor>? = null
            private set

        //是否保存到本地
        var isSaveLocal = false

        //写入的地址
        var localPath = ""

        fun addInterceptor(interceptor: ILogInterceptor): Builder {
            (interceptors
                ?: arrayListOf<ILogInterceptor>().also { interceptors = it })
                .add(interceptor)
            return this
        }

        fun builder(): LogConfig {
            if (isSaveLocal && localPath.isEmpty()) {
                throw RuntimeException("please config localPath in LogConfig")
            }
            if (logTag.isEmpty()) {
                logTag = "logger"
            }
            if (packageName.isEmpty()) {
                packageName = "com"
            }
            return LogConfig(this)
        }
    }
}
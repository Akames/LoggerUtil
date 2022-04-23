package com.akame.loggerutil

import android.app.Application
import com.akame.logger.LogConfig
import com.akame.logger.Logger

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.setConfig(LogConfig.Builder()
            .apply {
                isDebug = BuildConfig.DEBUG
                packageName = this@MyApplication.packageName
                printClassNameTag = true
                isSaveLocal = true
                localPath = externalCacheDir?.absolutePath ?: cacheDir.absolutePath
                addInterceptor(MyInterceptor())
            }
            .builder())
    }
}
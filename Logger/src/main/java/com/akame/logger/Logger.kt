package com.akame.logger

import android.util.Log

object Logger {
    private var logCore: LogCore? = null

    fun setConfig(config: LogConfig) {
        logCore = LogCore(config)
    }

    fun v(msg: String) {
        printLog(Log.VERBOSE, msg)
    }

    fun d(msg: String) {
        printLog(Log.DEBUG, msg)
    }

    fun i(msg: String) {
        printLog(Log.INFO, msg)
    }

    fun w(msg: String) {
        printLog(Log.WARN, msg)
    }

    fun e(msg: String) {
        printLog(Log.ERROR, msg)
    }

    fun a(msg: String) {
        printLog(Log.ASSERT, msg)
    }

    private fun printLog(priority: Int, msg: String) {
        if (logCore == null) {
            setConfig(LogConfig.Builder().builder())
        }
        logCore?.log(priority, msg)
    }
}
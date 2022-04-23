package com.akame.logger.interceptor

import com.akame.logger.*
import com.akame.logger.getStackTraceInfo
import com.akame.logger.isJson
import com.akame.logger.jsonPaster
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder

internal class CallStackInterceptor(
    private val printClassNameTag: Boolean,
    private val packageName: String
) : ILogInterceptor {
    private val headLine =
        "╔═══════════════════════════════════════════════════════════════════════════════════════"
    private val bottomLine =
        "╚═══════════════════════════════════════════════════════════════════════════════════════"
    private val leftBorder = "║"

    override fun log(priority: Int, tag: String, logMsg: String, chain: Chain) {
        val traceInfo = getStackTraceInfo(packageName)
        val newTag = if (printClassNameTag) traceInfo?.className ?: tag else tag
        chain.proceed(priority, newTag, headLine)
        traceInfo?.apply {
            val threadInfo = "ThreadName：${Thread.currentThread().name}"
            chain.proceed(priority, newTag, threadInfo.appendLeftBorder())
            val methodInfo = "Invoke：[(${className}:${lineNum})#${methodName}]"
            chain.proceed(priority, newTag, methodInfo.appendLeftBorder())
        }
        printMessage(priority, newTag, logMsg, chain)
        chain.proceed(priority, newTag, bottomLine)
    }

    private fun printMessage(priority: Int, tag: String, msg: String, chain: Chain) {
        val lines = msg.split("\n")
        lines.forEach { it ->
            if (it.isJson()) {
                val json = if (it.startsWith("{")) {
                    JSONObject(it).toString(4)
                } else {
                    JSONArray(it).toString(4)
                }
                json.jsonPaster()?.forEach {
                    chain.proceed(priority, tag, it.appendLeftBorder())
                }
            } else {
                chain.proceed(priority, tag, it.appendLeftBorder())
            }
        }
    }

    private fun String.appendLeftBorder(): String {
        return StringBuilder()
            .append(leftBorder)
            .append("\t")
            .append(this)
            .toString()
    }
}
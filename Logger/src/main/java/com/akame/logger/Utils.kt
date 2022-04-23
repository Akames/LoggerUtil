package com.akame.logger

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

internal fun getStackTraceInfo(packageName: String): StackTraceInfo? {
    val stackTrace = Thread.currentThread().stackTrace
    for (stackTraceElement in stackTrace) {
        if (stackTraceElement.className.startsWith(packageName)) {
            val className = stackTraceElement.fileName
            val methodName = stackTraceElement.methodName
            val lineNun = stackTraceElement.lineNumber
            return StackTraceInfo(className, methodName, lineNun)
        }
    }
    return null
}

internal data class StackTraceInfo(
    val className: String,
    val methodName: String,
    val lineNum: Int
)

internal fun String.isJson(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    return try {
        if (this.startsWith("[") && this.endsWith("]")) {
            JSONArray(this)
            true
        } else if (this.startsWith("{") && this.endsWith("}")) {
            JSONObject(this)
            true
        } else {
            false
        }
    } catch (e: JSONException) {
        e.printStackTrace()
        false
    }
}

internal fun String.jsonPaster(): Array<String>? {
    val message = System.getProperty("line.separator")?.plus(this)
    return System.getProperty("line.separator")?.let { message?.split(it)?.toTypedArray() }
}

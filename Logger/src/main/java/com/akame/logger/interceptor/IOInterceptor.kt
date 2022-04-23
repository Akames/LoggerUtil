package com.akame.logger.interceptor

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import com.akame.logger.Chain
import com.akame.logger.LogConfig
import okio.appendingSink
import okio.buffer
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.text.SimpleDateFormat

class IOInterceptor(private val localPath: String) : ILogInterceptor {
    private var localFile: File? = null
    private var handler: Handler? = null
    private val handlerThread = object : HandlerThread("IOLoggerHandlerThread") {
        override fun onLooperPrepared() {
            handler = object : Handler(looper) {
                override fun handleMessage(msg: Message) {
                    val logMsg = msg.obj as String
                    okIO(localPath, logMsg)
                }
            }
        }
    }

    override fun log(priority: Int, tag: String, logMsg: String, chain: Chain) {
        if (!handlerThread.isAlive) {
            handlerThread.start()
        }
        handler?.run {
            val msg = Message.obtain().apply { obj = logMsg }
            sendMessage(msg)
        }
        chain.proceed(priority, tag, logMsg)
    }

    private fun okIO(localPath: String, logMsg: String) {
        getLocalFile(localPath)?.apply {
            appendingSink()
                .buffer()
                .writeUtf8(logMsg)
                .writeUtf8("\n")
                .flush()
        }
    }

    private fun getLocalFile(localPath: String): File? {
        if (localFile == null) {
            try {
                val fileName = crateFileName(localPath)
                val file = File(fileName)
                if (!file.exists()) {
                    file.parentFile?.mkdirs()
                    file.createNewFile()
                }
                localFile = file
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return localFile
    }

    private fun crateFileName(localPath: String): String {
        val dataFormat = SimpleDateFormat.getDateInstance().format(System.currentTimeMillis())
        val today = dataFormat.format(System.currentTimeMillis())
        val fileName = "dir${File.separator}${today}.log"
        return StringBuilder().apply {
            append(localPath)
            if (!localPath.endsWith("/")) {
                append("/")
            }
            append(fileName)
        }.toString()
    }
}
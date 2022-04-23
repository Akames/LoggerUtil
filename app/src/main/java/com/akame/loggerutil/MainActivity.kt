package com.akame.loggerutil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akame.logger.LogConfig
import com.akame.logger.Logger
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testLogger()
    }

    private fun testLogger() {
        Logger.i("HellWorld!")
        Logger.d("HellWorld!")
        Logger.w("云深不知处!")
        Logger.e("天子笑坛中!")
        val userInfo = UserInfo(
            "展示",
            12,
            2,
            "吉水第二中学",
            "张三",
            "http://www.baidu.com"
        )
        val json = Gson().toJson(userInfo)
        Logger.a(json)
    }


    data class UserInfo(
        val name: String,
        val age: Int,
        val sex: Int,
        val schoolName: String,
        val teachName: String,
        val headImage: String
    )
}
package com.kk.android.jetwanandroid

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.kk.android.jetwanandroid.theme.WanAppTheme
import com.kk.android.jetwanandroid.utils.KLogger

class MainActivity : AppCompatActivity(), KLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { WanAppTheme { WanScreen() } }
    }
}
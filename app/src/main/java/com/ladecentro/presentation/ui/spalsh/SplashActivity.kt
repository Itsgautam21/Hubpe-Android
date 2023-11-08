package com.ladecentro.presentation.ui.spalsh

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.authentication.login.LoginActivity
import com.ladecentro.presentation.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    @Inject
    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (myPreference.getStoresTag(Intents.Token.name) == null) {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        } else {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        finish()
        setContent {
            LadecentroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                }
            }
        }
    }
}
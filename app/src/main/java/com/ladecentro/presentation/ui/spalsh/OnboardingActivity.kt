package com.ladecentro.presentation.ui.spalsh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.spalsh.compose.OnBoardingLayout

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {
                OnBoardingLayout()
            }
        }
    }
}


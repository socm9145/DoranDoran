package com.purple.hello

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.purple.core.designsystem.theme.HiTheme
import com.purple.hello.domain.account.CheckLoggedInUseCase
import com.purple.hello.ui.HiApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var checkLoggedInUseCase: CheckLoggedInUseCase

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HiTheme {
                HiApp(
                    windowSizeClass = calculateWindowSizeClass(this),
                    checkLoggedInUseCase = checkLoggedInUseCase,
                )
            }
        }
    }
}

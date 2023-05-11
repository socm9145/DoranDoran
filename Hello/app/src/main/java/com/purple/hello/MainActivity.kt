package com.purple.hello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.purple.core.designsystem.theme.HiTheme
import com.purple.hello.domain.account.CheckLoggedInUseCase
import com.purple.hello.domain.account.GetUserIdUseCase
import com.purple.hello.login.LoginScreen
import com.purple.hello.ui.HiApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var checkLoggedInUseCase: CheckLoggedInUseCase

    @Inject
    lateinit var getUserIdUseCase: GetUserIdUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HiTheme {
                HiApp(
                    windowSizeClass = calculateWindowSizeClass(this),
                    checkLoggedInUseCase = checkLoggedInUseCase,
                    getUserIdUseCase = getUserIdUseCase
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HiTheme {
        LoginScreen(
//            LoginViewModel()
        )
    }
}

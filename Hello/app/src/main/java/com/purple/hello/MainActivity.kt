package com.purple.hello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purple.hello.feature.rooms.RoomsRoute
import com.purple.hello.ui.theme.HelloTheme
import dagger.hilt.android.AndroidEntryPoint
import com.purple.core.designsystem.theme.HiTheme
import com.purple.hello.login.LoginScreen
import com.purple.hello.login.LoginViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LoginScreen(
//                        loginViewModel
                    )
                }
//                RoomsRoute()
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

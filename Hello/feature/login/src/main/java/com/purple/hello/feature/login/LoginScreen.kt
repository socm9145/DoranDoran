package com.purple.hello.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.purple.core.designsystem.theme.HiTheme
import com.purple.hello.feature.groups.R

@Composable
fun LoginScreen(
//    viewModel: LoginViewModel
) {
//    val isLoggedIn = viewModel.isLoggedIn.collectAsState()
    HiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                IconButton(
                    onClick = {
//                    viewModel.kakaoLogin()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.kakao_login_large_narrow),
                        contentDescription = "카카오 로그인 버튼",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(
//        viewModel = LoginViewModel(application = Application())
    )
}

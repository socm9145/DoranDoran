package com.purple.hello.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                HiLogoImage()
                SignInKakaoButton(
//                    viewModel = viewModel
                )
                SignInGoogleButton()
            }
        }
    }
}

@Composable
fun HiLogoImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .fillMaxHeight(0.2f)
            .padding(all = 24.dp),
    ) {
        Text(text = "LOGO IMAGE")
    }
}

@Composable
fun SignInKakaoButton(
//    viewModel: LoginViewModel
) {
    Surface(
        modifier = Modifier
            .padding(all = 8.dp),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
//                    viewModel.kakaoLogin()
                },
            painter = painterResource(id = R.drawable.kakao_login_large_wide),
            contentDescription = "카카오 로그인 버튼",
        )
    }
}

@Composable
fun SignInGoogleButton() {
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(
//        viewModel = LoginViewModel(application = Application())
    )
}

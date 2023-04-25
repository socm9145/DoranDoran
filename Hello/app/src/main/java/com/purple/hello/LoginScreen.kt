package com.purple.hello

import android.app.Application
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.purple.core.designsystem.theme.HiTheme

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
) {
    val isLoggedIn = loginViewModel.isLoggedIn.collectAsState()

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
                Spacer(modifier = Modifier.height(12.dp))
                SignInKakaoButton(
                    loginViewModel = loginViewModel,
                )
                Spacer(modifier = Modifier.height(12.dp))
                SignInGoogleButton()
            }
        }
    }
}

@Composable
fun HiLogoImage() {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .fillMaxHeight(0.2f)
            .padding(all = 24.dp),
    ) {
        Text(text = "LOGO IMAGE")
    }
}

@Composable
private fun SignInKakaoButton(
    loginViewModel: LoginViewModel,
) {
    SignInButton(
        onClick = {
            loginViewModel.kakaoLogin()
        },
        buttonContentColor = MaterialTheme.colorScheme.outlineVariant,
        painter = painterResource(id = R.drawable.ic_kakaotalk),
        contentText = "카카오 로그인",
    )
}

@Composable
private fun SignInGoogleButton() {
    SignInButton(
        onClick = { /*TODO*/ },
        buttonContentColor = MaterialTheme.colorScheme.onPrimary,
        painter = painterResource(id = R.drawable.ic_google_logo),
        contentText = "구글 로그인",
    )
}

@Composable
private fun SignInButton(
    onClick: () -> Unit,
    buttonContentColor: Color,
    painter: Painter,
    contentText: String,
) {
    HiTheme {
        Surface(
            modifier = Modifier
                .clickable { onClick() },
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surface),
            color = buttonContentColor,
            shape = RoundedCornerShape(6.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(40.dp)
                    .padding(vertical = 8.dp, horizontal = 10.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing,
                        ),
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier
                        .size(18.dp),
                    painter = painter,
                    contentDescription = contentText,
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = contentText,
                    color = MaterialTheme.colorScheme.outline,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(loginViewModel = LoginViewModel(Application()))
}

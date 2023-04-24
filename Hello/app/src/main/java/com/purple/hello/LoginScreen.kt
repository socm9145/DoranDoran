package com.purple.hello

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.api.ApiException
import com.purple.core.designsystem.component.SignInButton
import com.purple.core.designsystem.theme.HiTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
) {
    val isLoggedIn = loginViewModel.isLoggedIn.collectAsState()

    val coroutineScope = rememberCoroutineScope()
//    val user by remember(loginViewModel) { loginViewModel.user }.collectAsState()
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleAuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    Log.e(TAG, "구글 로그인 실패")
                } else {
                    coroutineScope.launch {
                        val idToken = account.idToken
                        Log.i(TAG, "구글 로그인 성공: $idToken")
                        /* TODO : send ID Token to server and validate */
                    }
                }
            } catch (e: ApiException) {
                Log.w(TAG, "구글 로그인 실패", e)
            }
        }

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
                SignInGoogleButton { authResultLauncher.launch(signInRequestCode) }
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
private fun SignInGoogleButton(
    onClick: () -> Unit,
) {
    SignInButton(
        onClick = onClick,
        buttonContentColor = MaterialTheme.colorScheme.onPrimary,
        painter = painterResource(id = R.drawable.ic_google_logo),
        contentText = "구글 로그인",
    )
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(loginViewModel = LoginViewModel(Application()))
}

package com.example.readerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.readerapp.ui.theme.ReaderAppTheme

/**
 * 앱을 켜면 가장 먼저 실행되는 곳입니다.
 * 여기서는 "자금레이더 색상 적용 -> 화면 보여주기"만 합니다.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReaderAppTheme {
                AppScreens()
            }
        }
    }
}

/** 이 앱에 있는 화면 목록입니다. 새 화면이 생기면 여기에 한 줄 추가하면 됩니다. */
private enum class Screen {
    HOME,               // 홈 화면
    COMPANY_REGISTER    // 회사 정보 등록 화면
}

/**
 * 어떤 화면을 보여줄지 정하는 곳입니다.
 *
 *   홈 화면에서 "회사등록" 버튼   -> 회사 정보 등록 화면으로 이동
 *   등록 화면에서 뒤로가기(←)     -> 홈 화면으로 돌아가기
 *   휴대폰 자체 뒤로가기 버튼      -> 홈 화면으로 돌아가기 (BackHandler)
 */
@Composable
private fun AppScreens() {
    // 지금 보고 있는 화면을 기억해 둡니다. (화면을 돌려도 유지됩니다.)
    var currentScreen by rememberSaveable { mutableStateOf(Screen.HOME) }

    when (currentScreen) {
        Screen.HOME -> {
            HomeScreen(
                onCompanyRegisterClick = { currentScreen = Screen.COMPANY_REGISTER }
            )
        }

        Screen.COMPANY_REGISTER -> {
            // 휴대폰 아래쪽(또는 옆으로 밀기) 뒤로가기도 홈으로 가게 합니다.
            BackHandler { currentScreen = Screen.HOME }

            CompanyRegisterScreen(
                onBack = { currentScreen = Screen.HOME }
            )
        }
    }
}

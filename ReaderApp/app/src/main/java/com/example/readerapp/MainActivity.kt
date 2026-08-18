package com.example.readerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.readerapp.ui.theme.ReaderAppTheme

/**
 * 앱을 켜면 가장 먼저 실행되는 곳입니다.
 * 여기서는 "자금레이더 색상 적용 -> 홈 화면 보여주기"만 합니다.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReaderAppTheme {
                HomeScreen()
            }
        }
    }
}

package com.example.readerapp

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.readerapp.ui.theme.Cream
import com.example.readerapp.ui.theme.Primary
import com.example.readerapp.ui.theme.SurfaceContainerLowest

/**
 * 우편번호(주소) 검색 창입니다.
 *
 * 카카오(다음) 우편번호 서비스를 그대로 띄웁니다.
 * 대한민국 도로명주소/지번주소를 검색할 수 있고, 별도 신청이나 키가 필요 없습니다.
 * 인터넷 연결이 필요합니다.
 *
 * @param onSelected 주소를 고르면 (우편번호, 주소)가 넘어옵니다.
 * @param onDismiss  창을 닫을 때 실행됩니다.
 * @param onError    주소 검색 서비스를 못 불러왔을 때 안내 문구가 넘어옵니다.
 */
@Composable
fun PostcodeDialog(
    onSelected: (zonecode: String, address: String) -> Unit,
    onDismiss: () -> Unit,
    onError: (String) -> Unit = {}
) {
    // 웹 화면에서 값이 넘어올 때 항상 "지금 시점의" 함수를 쓰도록 해줍니다.
    val currentOnSelected by rememberUpdatedState(onSelected)
    val currentOnError by rememberUpdatedState(onError)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f),
            shape = RoundedCornerShape(16.dp),
            color = Cream
        ) {
            Column {
                // 창 맨 위: 제목 + 닫기(X)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "주소 검색",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "닫기",
                            tint = Primary
                        )
                    }
                }

                // 실제 주소 검색 화면 (웹 화면을 그대로 띄웁니다)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SurfaceContainerLowest
                ) {
                    PostcodeWebView(
                        onSelected = { zonecode, address ->
                            currentOnSelected(zonecode, address)
                        },
                        onError = { message -> currentOnError(message) }
                    )
                }
            }
        }
    }
}

/**
 * 주소 검색 웹 화면입니다.
 * assets/postcode.html 파일을 띄우고, 거기서 고른 주소를 넘겨받습니다.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun PostcodeWebView(
    onSelected: (String, String) -> Unit,
    onError: (String) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true

                // 웹 화면 -> 안드로이드로 값을 넘겨주는 통로입니다.
                addJavascriptInterface(
                    PostcodeBridge(
                        selectedHandler = { zonecode, address ->
                            // 웹에서 온 신호는 다른 흐름에서 오므로
                            // 화면을 바꾸는 일은 화면 담당 흐름으로 넘겨줍니다.
                            post { onSelected(zonecode, address) }
                        },
                        errorHandler = { message -> post { onError(message) } }
                    ),
                    "AndroidBridge"
                )

                // assets 안의 HTML을 읽어서, "다음 우편번호" 주소를 기준으로 띄웁니다.
                //
                // file:/// 로 직접 열면 브라우저가 이 페이지의 출처를 "없음"으로 보기 때문에
                // 카카오 우편번호 스크립트가 화면을 못 그리고 빈 창만 나옵니다.
                // 그래서 아래처럼 기준 주소를 붙여서 띄웁니다.
                val html = context.assets.open("postcode.html")
                    .bufferedReader()
                    .use { it.readText() }

                loadDataWithBaseURL(
                    "https://postcode.map.daum.net",
                    html,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        }
    )
}

/** 웹 화면에서 부르는 함수들입니다. (JavaScript -> Kotlin) */
private class PostcodeBridge(
    private val selectedHandler: (String, String) -> Unit,
    private val errorHandler: (String) -> Unit
) {
    @JavascriptInterface
    fun onAddressSelected(zonecode: String, address: String) {
        selectedHandler(zonecode, address)
    }

    @JavascriptInterface
    fun onError(message: String) {
        errorHandler(message)
    }
}

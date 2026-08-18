package com.example.readerapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// 자금레이더는 항상 이 색조합만 사용합니다.
// (휴대폰 배경화면에 따라 색이 바뀌는 "다이나믹 컬러" 기능은 껐습니다.
//  디자인대로 정확히 보이게 하기 위해서입니다.)
private val AppColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary,
    secondaryContainer = SecondaryContainer,
    background = Cream,
    onBackground = OnSurface,
    surface = Cream,
    onSurface = OnSurface,
    surfaceVariant = SurfaceContainerLowest,
    onSurfaceVariant = OnSurfaceVariant,
    outlineVariant = OutlineVariant
)

@Composable
fun ReaderAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}

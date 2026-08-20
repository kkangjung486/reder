package com.example.readerapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readerapp.ui.theme.Cream
import com.example.readerapp.ui.theme.OnPrimary
import com.example.readerapp.ui.theme.OnSurface
import com.example.readerapp.ui.theme.OnSurfaceVariant
import com.example.readerapp.ui.theme.OutlineVariant
import com.example.readerapp.ui.theme.Primary
import com.example.readerapp.ui.theme.PrimaryContainer
import com.example.readerapp.ui.theme.ReaderAppTheme
import com.example.readerapp.ui.theme.Secondary
import com.example.readerapp.ui.theme.SecondaryContainer
import com.example.readerapp.ui.theme.SurfaceContainerLowest
import java.util.Calendar

/**
 * 자금레이더 홈 화면.
 *
 * 화면은 크게 4덩어리입니다.
 *   1) 맨 위 제목 영역          -> Header()
 *   2) "스마트 매칭" 소개 카드   -> IntroCard()
 *   3) 큰 버튼 3개 + 작은 카드 2개
 *   4) 맨 아래 탭 바            -> BottomBar()
 *
 * @param onCompanyRegisterClick "회사등록" 버튼을 눌렀을 때 실행할 동작입니다.
 *        넘겨주지 않으면 아무 일도 일어나지 않습니다. (미리보기에서 쓰입니다.)
 */
@Composable
fun HomeScreen(onCompanyRegisterClick: () -> Unit = {}) {
    Scaffold(
        containerColor = Cream,
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Header()

            // 내용이 화면보다 길어지면 위아래로 스크롤됩니다.
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                IntroCard(modifier = Modifier.padding(horizontal = 24.dp))

                // 큰 버튼 3개
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FilledBigButton(
                        icon = Icons.Filled.Payments,
                        text = "신청가능 자금"
                    )
                    OutlinedBigButton(
                        icon = Icons.Filled.SupportAgent,
                        text = "문의 남기기"
                    )
                    // 누르면 "회사 정보 등록" 화면으로 이동합니다.
                    OutlinedBigButton(
                        icon = Icons.Filled.Apartment,
                        text = "회사등록",
                        onClick = onCompanyRegisterClick
                    )
                }

                // 옆으로 밀어서 보는 작은 카드들
                SmallCardRow()

                Spacer(modifier = Modifier.height(8.dp))
            }

            TodayLabel()
        }
    }
}

/* ---------------------------------------------------------------
 * 1) 맨 위 제목 영역
 * --------------------------------------------------------------- */
@Composable
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Cream)
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Text(
            text = "자금레이더",
            fontSize = 24.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "우리회사에 꼭 맞는 정책자금 알림서비스",
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = OnSurfaceVariant
        )
    }
}

/* ---------------------------------------------------------------
 * 2) "스마트 매칭" 소개 카드
 * --------------------------------------------------------------- */
@Composable
private fun IntroCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, Primary.copy(alpha = 0.10f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box {
            // 오른쪽 위 은은한 노란 동그라미 장식
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 40.dp, y = (-40).dp)
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(SecondaryContainer.copy(alpha = 0.25f))
            )

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Analytics,
                            contentDescription = null,
                            tint = OnPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Text(
                        text = "스마트 매칭",
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Primary
                    )
                }
                Text(
                    text = "복잡한 서류 없이, 우리 기업 조건에 맞는 " +
                        "최적의 정부지원금을 실시간으로 분석합니다.",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = OnSurfaceVariant
                )
            }
        }
    }
}

/* ---------------------------------------------------------------
 * 3-1) 진한 초록 큰 버튼
 * --------------------------------------------------------------- */
@Composable
private fun FilledBigButton(icon: ImageVector, text: String, onClick: () -> Unit = {}) {
    Button(
        // onClick 을 넘겨주지 않으면 아직 아무 동작도 하지 않습니다.
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = OnPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/* ---------------------------------------------------------------
 * 3-2) 테두리만 있는 큰 버튼
 * --------------------------------------------------------------- */
@Composable
private fun OutlinedBigButton(icon: ImageVector, text: String, onClick: () -> Unit = {}) {
    OutlinedButton(
        // onClick 을 넘겨주지 않으면 아직 아무 동작도 하지 않습니다.
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Primary),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = Primary
        )
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/* ---------------------------------------------------------------
 * 3-3) 옆으로 미는 작은 카드 2개
 * --------------------------------------------------------------- */
@Composable
private fun SmallCardRow() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { SmallCard(Icons.Filled.Description, "필수 서류 안내") }
        item { SmallCard(Icons.Filled.History, "과거 지원 이력") }
    }
}

@Composable
private fun SmallCard(icon: ImageVector, label: String) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Secondary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = label,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Medium,
                color = OnSurface
            )
        }
    }
}

/* ---------------------------------------------------------------
 * 오늘 날짜 (화면 아래 가운데, 흐리게)
 * --------------------------------------------------------------- */
@Composable
private fun TodayLabel() {
    val today = Calendar.getInstance()
    val text = "${today.get(Calendar.YEAR)}년 " +
        "${today.get(Calendar.MONTH) + 1}월 " +
        "${today.get(Calendar.DAY_OF_MONTH)}일"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = OnSurfaceVariant.copy(alpha = 0.6f)
        )
    }
}

/* ---------------------------------------------------------------
 * 4) 맨 아래 탭 바
 * --------------------------------------------------------------- */
@Composable
private fun BottomBar() {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(Icons.Filled.Home, "홈", selected = true)
            BottomBarItem(Icons.Filled.Search, "자금조회", selected = false)
            BottomBarItem(Icons.Filled.ChatBubble, "문의", selected = false)
            BottomBarItem(Icons.Filled.Settings, "설정", selected = false)
        }
    }
}

@Composable
private fun BottomBarItem(icon: ImageVector, label: String, selected: Boolean) {
    // 선택된 탭은 진하게, 나머지는 흐리게 보여줍니다.
    val color = if (selected) Secondary else OnSurfaceVariant.copy(alpha = 0.6f)

    Column(
        modifier = Modifier.width(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = color
        )
    }
}

/* ---------------------------------------------------------------
 * 안드로이드 스튜디오 미리보기 (Split / Design 탭에서 보임)
 * --------------------------------------------------------------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ReaderAppTheme {
        HomeScreen()
    }
}

package com.example.readerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readerapp.ui.theme.Cream
import com.example.readerapp.ui.theme.ErrorRed
import com.example.readerapp.ui.theme.OnPrimary
import com.example.readerapp.ui.theme.OnSurface
import com.example.readerapp.ui.theme.OnSurfaceVariant
import com.example.readerapp.ui.theme.OutlineVariant
import com.example.readerapp.ui.theme.Primary
import com.example.readerapp.ui.theme.ReaderAppTheme
import com.example.readerapp.ui.theme.SurfaceContainerLowest

/**
 * 회사 정보 등록 화면.
 *
 * 화면은 크게 3덩어리입니다.
 *   1) 맨 위 뒤로가기(←) + 제목  -> TopBar()
 *   2) 가운데 입력칸들            -> 아래 본문 Column
 *   3) 맨 아래 "등록 완료" 버튼   -> SubmitBar()
 *
 * @param onBack 뒤로가기(←)를 눌렀을 때 실행할 동작입니다. 홈 화면으로 돌아갑니다.
 */
@Composable
fun CompanyRegisterScreen(onBack: () -> Unit = {}) {
    // 입력한 값들을 기억해 둡니다. (화면을 돌려도 값이 지워지지 않습니다.)
    var companyName by rememberSaveable { mutableStateOf("") }
    var repName by rememberSaveable { mutableStateOf("") }
    var contact by rememberSaveable { mutableStateOf("") }
    var industry by rememberSaveable { mutableStateOf("") }       // 업종 (목록에서 고름)
    var zonecode by rememberSaveable { mutableStateOf("") }        // 우편번호 (검색해서 채움)
    var address by rememberSaveable { mutableStateOf("") }         // 도로명 주소 (검색해서 채움)
    var addressDetail by rememberSaveable { mutableStateOf("") }   // 상세주소 (직접 입력)
    var employees by rememberSaveable { mutableStateOf("") }       // 직원 수 (목록에서 고름)
    var revenue by rememberSaveable { mutableStateOf("") }

    // 어떤 창이 열려 있는지 기억합니다.
    var showIndustryPicker by rememberSaveable { mutableStateOf(false) }
    var showEmployeePicker by rememberSaveable { mutableStateOf(false) }
    var showPostcodeSearch by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    // 고를 수 있는 항목들 (한 번만 만들어 두고 계속 씁니다.)
    val industryOptions = remember { KSIC_INDUSTRIES.map { it.label } }
    val employeeOptions = remember { (1..100).map { "${it}명" } }

    // 홈 화면과 똑같은 방식입니다.
    // 위/아래 막대를 Scaffold 슬롯에 넣지 않고 본문 안에 직접 넣습니다.
    // (슬롯에 넣으면 상태바 여백이 적용되지 않아 뒤로가기 버튼이 시계에 가려집니다.)
    Scaffold(containerColor = Cream) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                // 키보드가 올라와도 입력칸이 가려지지 않게 합니다.
                .imePadding()
        ) {
            TopBar(onBack = onBack)

            // 내용이 화면보다 길어지면 위아래로 스크롤됩니다.
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
            // 안내 문구
            Text(
                text = "정확한 정책자금 매칭을 위해\n회사 정보를 입력해주세요.",
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = OnSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "입력하신 정보는 안전하게 보관됩니다.",
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            InputField(
                value = companyName,
                onValueChange = { companyName = it },
                label = "회사명"
            )
            InputField(
                value = repName,
                onValueChange = { repName = it },
                label = "대표님 성함"
            )
            InputField(
                value = contact,
                onValueChange = { contact = it },
                label = "연락처 (010-1234-5678)",
                keyboardType = KeyboardType.Phone
            )
            // 업종: 직접 쓰지 않고 한국표준산업분류 목록에서 고릅니다.
            PickerField(
                value = industry,
                label = "업종 (한국표준산업분류)",
                onClick = { showIndustryPicker = true }
            )

            // 우편번호 + 오른쪽에 "주소 찾기" 버튼
            Row(verticalAlignment = Alignment.Bottom) {
                InputField(
                    value = zonecode,
                    onValueChange = { },
                    label = "우편번호",
                    readOnly = true,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedButton(
                    onClick = { showPostcodeSearch = true },
                    modifier = Modifier.padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "주소 찾기",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // 검색으로 채워지는 주소 (직접 고칠 수 없습니다)
            InputField(
                value = address,
                onValueChange = { },
                label = "회사 주소",
                readOnly = true
            )

            // 상세주소는 직접 입력합니다. (예: 3층 301호)
            InputField(
                value = addressDetail,
                onValueChange = { addressDetail = it },
                label = "상세주소 (예: 3층 301호)"
            )

            // 주소 검색에 문제가 있을 때만 보여줍니다.
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = ErrorRed,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // 직원 수 / 연매출은 한 줄에 나란히 보여줍니다.
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // 직원 수: 1명 ~ 100명 중에서 고릅니다.
                PickerField(
                    value = employees,
                    label = "직원 수",
                    onClick = { showEmployeePicker = true },
                    modifier = Modifier.weight(1f)
                )
                InputField(
                    value = revenue,
                    onValueChange = { revenue = it },
                    label = "연매출 (억원)",
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f)
                )
            }

                Spacer(modifier = Modifier.height(24.dp))
            }

            SubmitBar()
        }
    }

    /* --- 눌렀을 때 뜨는 창들 --- */

    // 업종 고르기
    if (showIndustryPicker) {
        PickerDialog(
            title = "업종 선택 (한국표준산업분류)",
            options = industryOptions,
            selected = industry,
            onSelect = {
                industry = it
                showIndustryPicker = false
            },
            onDismiss = { showIndustryPicker = false }
        )
    }

    // 직원 수 고르기 (1명 ~ 100명)
    if (showEmployeePicker) {
        PickerDialog(
            title = "직원 수 선택",
            options = employeeOptions,
            selected = employees,
            onSelect = {
                employees = it
                showEmployeePicker = false
            },
            onDismiss = { showEmployeePicker = false }
        )
    }

    // 우편번호로 주소 찾기
    if (showPostcodeSearch) {
        PostcodeDialog(
            onSelected = { foundZonecode, foundAddress ->
                zonecode = foundZonecode
                address = foundAddress
                errorMessage = ""
                showPostcodeSearch = false
            },
            onDismiss = { showPostcodeSearch = false },
            onError = { message ->
                errorMessage = message
                showPostcodeSearch = false
            }
        )
    }
}

/* ---------------------------------------------------------------
 * 1) 맨 위 뒤로가기(←) + 제목
 * --------------------------------------------------------------- */
@Composable
private fun TopBar(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Cream)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(48.dp)
    ) {
        // 왼쪽 뒤로가기 버튼
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(40.dp)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "뒤로가기",
                tint = Primary,
                modifier = Modifier.size(24.dp)
            )
        }

        // 가운데 제목
        Text(
            text = "회사 정보 등록",
            fontSize = 24.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Primary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

/* ---------------------------------------------------------------
 * 2) 입력칸 하나 (밑줄 스타일 + 위로 떠오르는 라벨)
 * --------------------------------------------------------------- */
@Composable
private fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        singleLine = true,
        readOnly = readOnly,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        // 배경은 없애고 밑줄만 남겨서 디자인처럼 깔끔하게 보이게 합니다.
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedIndicatorColor = Primary,
            unfocusedIndicatorColor = OutlineVariant,
            focusedLabelColor = Primary,
            unfocusedLabelColor = OnSurfaceVariant,
            focusedTextColor = OnSurface,
            unfocusedTextColor = OnSurface,
            cursorColor = Primary
        )
    )
}

/* ---------------------------------------------------------------
 * 2-2) 목록에서 고르는 칸 (업종, 직원 수)
 *
 * 생김새는 다른 입력칸과 똑같지만, 키보드가 뜨지 않고
 * 어디를 누르든 고르는 창이 열립니다.
 * --------------------------------------------------------------- */
@Composable
private fun PickerField(
    value: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        InputField(
            value = value,
            onValueChange = { },
            label = label,
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = Primary
                )
            }
        )
        // 입력칸 위에 투명한 판을 덮어 둡니다.
        // 이렇게 해야 칸 아무 데나 눌러도 고르는 창이 열립니다.
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(onClick = onClick)
                // 덮개가 아래 글자를 가리므로, 화면 낭독기가 읽을 내용을 따로 알려줍니다.
                .semantics {
                    contentDescription = if (value.isEmpty()) label else "$label: $value"
                }
        )
    }
}

/* ---------------------------------------------------------------
 * 3) 맨 아래 "등록 완료" 버튼
 * --------------------------------------------------------------- */
@Composable
private fun SubmitBar() {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        shadowElevation = 8.dp
    ) {
        Box(modifier = Modifier.padding(24.dp)) {
            Button(
                // 아직 아무 동작도 하지 않습니다. 나중에 여기에 저장 기능을 넣으면 됩니다.
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary
                )
            ) {
                Text(
                    text = "등록 완료",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/* ---------------------------------------------------------------
 * 안드로이드 스튜디오 미리보기 (Split / Design 탭에서 보임)
 * --------------------------------------------------------------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CompanyRegisterScreenPreview() {
    ReaderAppTheme {
        CompanyRegisterScreen()
    }
}

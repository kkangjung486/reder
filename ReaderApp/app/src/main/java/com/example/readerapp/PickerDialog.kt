package com.example.readerapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.readerapp.ui.theme.Cream
import com.example.readerapp.ui.theme.OnSurface
import com.example.readerapp.ui.theme.OutlineVariant
import com.example.readerapp.ui.theme.Primary
import com.example.readerapp.ui.theme.SecondaryContainer

/**
 * 목록에서 하나를 고르는 창입니다. (업종, 직원 수에 함께 씁니다.)
 *
 * @param title        창 맨 위 제목
 * @param options      고를 수 있는 항목들
 * @param selected     지금 골라져 있는 항목 (없으면 빈 글자)
 * @param onSelect     항목을 고르면 실행됩니다.
 * @param onDismiss    창을 닫을 때 실행됩니다.
 */
@Composable
fun PickerDialog(
    title: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val listState = rememberLazyListState()

    // 이미 고른 항목이 있으면 그 자리로 목록을 미리 스크롤해 둡니다.
    LaunchedEffect(Unit) {
        val index = options.indexOf(selected)
        if (index > 0) {
            listState.scrollToItem(index)
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Cream
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                )
                HorizontalDivider(color = OutlineVariant)

                LazyColumn(
                    state = listState,
                    modifier = Modifier.heightIn(max = 420.dp)
                ) {
                    itemsIndexed(options) { index, option ->
                        val isSelected = option == selected

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelect(option) }
                                .padding(horizontal = 20.dp, vertical = 14.dp)
                        ) {
                            Text(
                                text = option,
                                fontSize = 16.sp,
                                lineHeight = 22.sp,
                                // 고른 항목은 진하게 보여줍니다.
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Primary else OnSurface
                            )
                        }

                        if (isSelected) {
                            HorizontalDivider(
                                color = SecondaryContainer,
                                thickness = 2.dp
                            )
                        } else if (index < options.lastIndex) {
                            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.4f))
                        }
                    }
                }
            }
        }
    }
}

package com.purple.hello.feature.setting.profile

import android.icu.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.purple.core.designsystem.component.HiDropDownTextField
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme

@Composable
fun ProfileSettingRoute(
    isFirst: Boolean,
    gridContent: LazyGridScope.() -> Unit,
) {
    HiTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        ) {
            ProfileSettingAppBar(isFirst = isFirst)
            ProfileSettingScreen(isFirst = isFirst, gridContent)
        }
    }
}

@Composable
private fun ProfileSettingScreen(
    isFirst: Boolean,
    gridContent: LazyGridScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Unspecified),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally),
        ) {
            // TODO : isFirst->빈 이미지, !isFirst->기존 프로필 이미지
            if (!isFirst) {
                Image(
                    painter = painterResource(id = R.drawable.rooster),
                    contentDescription = "ProfileImage",
                )
            }
        }
        SelectBirth()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = LazyGridState(),
            contentPadding = PaddingValues(all = 12.dp),
            content = gridContent,
        )
    }
}

@Composable
private fun SelectBirth() {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
    val currentDay = Calendar.getInstance().get(Calendar.DATE)
    val years = (1920..currentYear).toList().map { it.toString() }
    val months = (1..12).toList().map { it.toString() }

    var selectedYear by remember { mutableStateOf(currentYear) }
    var selectedMonth by remember { mutableStateOf(currentMonth) }
    var selectedDay by remember { mutableStateOf(currentDay) }
    var days = (1..getDaysInMonth(selectedYear.toInt(), selectedMonth.toInt())).toList().map { it.toString() }

    var yearExpanded by remember { mutableStateOf(false) }
    var monthExpanded by remember { mutableStateOf(false) }
    var dayExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 12.dp),
    ) {
        Text(
            text = "생년월일을 선택해주세요!",
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
    ) {
        Row {
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .weight(1.25f)
                    .height(50.dp),
            ) {
                HiDropDownTextField(
                    value = selectedYear.toString(),
                    onValueChange = { selectedYear = it },
                    iconClick = { yearExpanded = true },
                    expanded = yearExpanded,
                    onDismiss = { yearExpanded = false },
                    content = years,
                    itemClick = {
                        selectedYear = it
                        yearExpanded = false
                    },
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .weight(1f)
                    .height(50.dp),
            ) {
                HiDropDownTextField(
                    value = selectedMonth.toString(),
                    onValueChange = { selectedMonth = it },
                    iconClick = { monthExpanded = true },
                    expanded = monthExpanded,
                    onDismiss = { monthExpanded = false },
                    content = months,
                    itemClick = {
                        selectedMonth = it
                        monthExpanded = false
                    },
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .weight(1f)
                    .height(50.dp),
            ) {
                HiDropDownTextField(
                    value = selectedDay.toString(),
                    onValueChange = { selectedDay = it },
                    iconClick = { dayExpanded = true },
                    expanded = dayExpanded,
                    onDismiss = { dayExpanded = false },
                    content = days,
                    itemClick = {
                        selectedDay = it
                        dayExpanded = false
                    },
                )
            }
        }
    }
}

private fun getDaysInMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

@Composable
private fun ProfileSettingAppBar(
    isFirst: Boolean,
) {
    HiTopAppBar(
        title = "프로필 설정",
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로 가기",
        actions = {
            Box(
                modifier = Modifier
                    .background(Color.Unspecified)
                    .padding(all = 16.dp)
                    .clickable {
//                        TODO : 이미지, 생일 저장 후 페이지 이동 / isFirst->룸으로 이동? !isFirst->세팅으로 이동
                    },
            ) { Text(text = "저장하기") }
        },
        onNavigationClick = { /* TODO : 뒤로가기 */ },
    )
}

@Preview
@Composable
private fun PreviewProfileSetting() {
    ProfileSettingRoute(isFirst = false, gridContent = {})
}

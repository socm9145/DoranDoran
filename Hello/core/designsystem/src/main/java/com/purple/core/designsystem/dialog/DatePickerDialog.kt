package com.purple.core.designsystem.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.purple.core.designsystem.theme.HiTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HiDatePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now().minusDays(3)) }

    HiTheme {
        Box {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.surface,
            ) {
                CalendarView(
                    useCaseState = rememberUseCaseState(
                        onCloseRequest = { onDismiss() },
                        onFinishedRequest = { onConfirm() },
                        onDismissRequest = { onDismiss() },
                    ),
                    config = CalendarConfig(
                        yearSelection = true,
                        monthSelection = true,
                        style = CalendarStyle.MONTH,
                    ),
                    selection = CalendarSelection.Date(
                        selectedDate = selectedDate.value
                    ) { newDate ->
                        selectedDate.value = newDate
                    },
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewHiDatePickerDialog() {
    HiDatePickerDialog(onDismiss = {}, onConfirm = {})
}

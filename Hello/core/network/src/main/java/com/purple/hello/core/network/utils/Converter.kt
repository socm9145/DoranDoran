package com.purple.hello.core.network.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTime(): LocalDateTime? {
    val formatter = DateTimeFormatterBuilder()
        .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE)
        .toFormatter()
    return try {
        LocalDateTime.parse(this, formatter)
    } catch (e: DateTimeParseException) {
        null
    }
}

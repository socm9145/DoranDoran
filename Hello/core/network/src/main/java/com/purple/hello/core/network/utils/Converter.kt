package com.purple.hello.core.network.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.toDate() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(this)

fun String.birthToDate() = SimpleDateFormat("yyyy-MM-dd").parse(this)

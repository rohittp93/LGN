package com.lgn.presentation.ui.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun convertToDisplayDate(milliSeconds: Long): String {
    val dateFormat = "dd/MM/yyyy hh:mm"
    val formatter = SimpleDateFormat(dateFormat)
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}

fun convertDayToDate(milliSeconds: Long): Date {
    val dateFormat = "dd/MM/yyyy"
    val formatter = SimpleDateFormat(dateFormat)
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    val stringDate = formatter.format(calendar.time)

    return try {
        formatter.parse(stringDate) as Date
    } catch (e: ParseException) {
        e.printStackTrace()
        Calendar.getInstance().time
    }
}

fun convertDateToString(date: Date?): String {
    val simpleDate = SimpleDateFormat("dd/MM/yy hh:mm a")
    date?. let {
        return simpleDate.format(date)
    } ?: run { return "" }
}
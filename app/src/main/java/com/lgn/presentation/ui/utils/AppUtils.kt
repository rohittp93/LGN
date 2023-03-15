package com.lgn.presentation.ui.utils

import android.content.Context
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*


fun convertToMonthAndYear(month: Int, year: Int): String {
    val dateFormat = "MMM yyyy"
    val formatter = SimpleDateFormat(dateFormat)
    val calendar: Calendar = Calendar.getInstance()
    calendar.set(year, month - 1, 1, 0, 0);
    return formatter.format(calendar.time)
}


fun getStatusFromFilter(status: String) = when (status) {
    "Active" -> 1
    "Inactive" -> 0
    else -> {
        -1
    }
}
fun getFilterFromStatus(status: Int? = -1) = when (status) {
    1 -> "Active"
    0 -> "Inactive"
    else -> {
        "Both"
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun convertToMonthAndYear(monthYear: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(monthYear)
    val dateFormated =
        SimpleDateFormat("MMM yyyy").format(sdf)
    return dateFormated
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
    date?.let {
        return simpleDate.format(date)
    } ?: run { return "" }
}
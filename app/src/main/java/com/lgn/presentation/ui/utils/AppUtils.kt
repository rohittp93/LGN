package com.lgn.presentation.ui.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.*
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

fun convertToMonthYear(year: String) : String {
    val date = SimpleDateFormat("MM-yyyy").parse("01-$year")
    val dateFormated =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date)
    return dateFormated
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


fun convertToYear(monthYear: String?): String {
    monthYear?.let {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(monthYear)
        val dateFormated =
            SimpleDateFormat("yyyy").format(sdf)
        return dateFormated
    } ?: run {
        val calendar = GregorianCalendar()
        calendar.time = Date()

        return calendar.get(Calendar.YEAR).toString()
    }
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

val WindowInsets.Companion.isImeVisible: Boolean
    @Composable
    get() {
        val density = LocalDensity.current
        val ime = this.ime
        return remember {
            derivedStateOf {
                ime.getBottom(density) > 0
            }
        }.value
    }

fun convertDateToString(date: Date?): String {
    val simpleDate = SimpleDateFormat("dd/MM/yy hh:mm a")
    date?.let {
        return simpleDate.format(date)
    } ?: run { return "" }
}

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}



internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}
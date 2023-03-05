package com.lgn.presentation.ui.utils

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.datepicker.MaterialDatePicker
import com.lgn.presentation.ui.theme.textColorGray
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatePickerview(
    datePicked: String?,
    updatedDate: (date: Date?) -> Unit,
    label: String,
    selectable: Boolean? = true
) {
    val activity = LocalContext.current as AppCompatActivity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .border(0.5.dp, textColorGray.copy(alpha = 0.5f))
            .padding(top = 14.dp, bottom = 14.dp, start = 10.dp, end = 10.dp)
            .clickable {
                if (selectable == true) {
                    showDatePicker(activity, updatedDate)
                }
            }
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = datePicked?.let {
                    "$label - $it"
                } ?: run { label },
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 3.5.sp
                ),
                fontSize = 14.sp,
                color = textColorGray
            )
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp),
                tint = textColorGray
            )
        }
    }
}

private fun showDatePicker(
    activity: AppCompatActivity,
    updatedDate: (Date?) -> Unit
) {
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        var date = convertDayToDate(it)
        //Date(int year, int month, int date, int hrs, int min)
        val timePickerDialog = TimePickerDialog(
            activity,
            { _, hour: Int, minute: Int ->
                //time.value = "$hour:$minute"
                date.hours = hour
                date.minutes = minute
                updatedDate(date)
            }, 0, 0, false
        )
        timePickerDialog.show()
    }
}
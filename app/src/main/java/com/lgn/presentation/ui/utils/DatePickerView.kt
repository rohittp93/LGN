package com.lgn.presentation.ui.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lgn.presentation.ui.theme.borderColorGray
import com.lgn.presentation.ui.theme.textColorGray
import com.lgn.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatePickerview(
    label: String,
    selectable: Boolean? = true,
    onSelectYearClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .wrapContentSize(Alignment.TopStart)
            .border(0.5.dp, textColorGray.copy(alpha = 0.5f))
            .padding(top = 14.dp, bottom = 14.dp, start = 10.dp, end = 10.dp)
            .clickable {
                if (selectable == true) {
                    onSelectYearClicked()
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
                text = label,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                ),
                fontSize = 14.sp,
                color = borderColorGray
            )
            Icon(
                painter = painterResource(id = R.drawable.calendar),
                "Calendar",
                Modifier.width(16.dp),
                tint = borderColorGray
            )
        }
    }
}
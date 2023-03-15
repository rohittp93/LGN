package com.lgn.presentation.dashboard.metrics.allmetrics

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lgn.domain.model.StudentData
import com.lgn.presentation.dashboard.metrics.studentmetricsdetail.StudentMetricsDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@OptIn(ExperimentalMaterialApi::class)
@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun StudentItem(
    student: StudentData,
    viewModel: StudentMetricsDetailViewModel = hiltViewModel(),
    onTableClicked: ((StudentData) -> Unit),
) {
    Card(
        shape = MaterialTheme.shapes.small,
        backgroundColor = Color.White,
        onClick = {
            onTableClicked(student)
        },
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = 3.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row {
                Box(modifier = Modifier.height(85.dp)) {

                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 0.dp)) {
                    Text(
                        text = student.userName ?: "",
                        color = Color.DarkGray,
                        fontSize = 22.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Text(
                        text = student.role ?: "",
                        color = Color.DarkGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
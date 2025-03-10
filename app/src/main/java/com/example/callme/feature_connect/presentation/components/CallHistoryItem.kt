package com.example.callme.feature_connect.presentation.components

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.callme.core.domain.model.UserCall
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime


@Composable
fun CallHistoryItem(
    userCall: UserCall,
) {
    val startedAt = userCall.startedAt
    val duration = userCall.endedAt - userCall.startedAt

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = Color.Cyan
            )
            .padding(
                vertical = 20.dp,
                horizontal = 8.dp,
            )
    ) {
        Text(
            text = formatStartedAt(startedAt)
        )
        Text(
            text = formatDuration(duration)
        )
    }
}

fun isYesterday(time: Long): Boolean {
    return isToday(time + DateUtils.DAY_IN_MILLIS)
}

fun isToday(time: Long): Boolean {
    val now = System.currentTimeMillis()

    return now - time <= DateUtils.DAY_IN_MILLIS
}

fun formatDuration(durationInMs: Long): String {

    val date = Instant.fromEpochMilliseconds((durationInMs))
        .toLocalDateTime(
            timeZone = TimeZone.UTC
        )

    val hoursStr = if (date.hour > 0) {
        "${date.hour}h "
    } else {
        ""
    }

    val minutesStr = if (date.minute > 0) {
        "${date.minute}m "
    } else {
        ""
    }

    val secondsStr = if (date.second > 0) {
        "${date.second}s"
    } else {
        ""
    }

    return "$hoursStr$minutesStr$secondsStr"
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun formatStartedAt(startedAt: Long): String {
    val date = Instant.fromEpochMilliseconds((startedAt))
        .toLocalDateTime(
            timeZone = TimeZone.UTC
        )
    val nowDate = Clock.System.now()
        .toLocalDateTime(
            timeZone = TimeZone.UTC
        )

    val timeStr = date.format(LocalDateTime.Format {
        byUnicodePattern("HH:MM")
    })

    val dateStr = if (isToday(startedAt)) {
        "Today"
    } else if (isYesterday(startedAt)) {
        "Yesterday"
    } else if (nowDate.year == date.year) {
        "${date.month} ${date.dayOfMonth}"

        date.format(LocalDateTime.Format {
            monthName(MonthNames.ENGLISH_FULL)
            char(' ')
            dayOfMonth()
        })
    } else {
        date.format(LocalDateTime.Format {
            byUnicodePattern("dd/MM/yyyy")
        })
    }

    return "$dateStr, $timeStr"
}

@Preview(showBackground = true)
@Composable
fun CallHistoryItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        val now = System.currentTimeMillis()
        val yesterday = now - DateUtils.DAY_IN_MILLIS
        val beforeYesterday = yesterday - DateUtils.DAY_IN_MILLIS
        val lastMonth = now - DateUtils.DAY_IN_MILLIS * 30
        val lastYear = now - DateUtils.DAY_IN_MILLIS * 365

        val delays = listOf(
            DateUtils.HOUR_IN_MILLIS,
            DateUtils.HOUR_IN_MILLIS * 2,
            DateUtils.HOUR_IN_MILLIS + DateUtils.MINUTE_IN_MILLIS * 10,
            DateUtils.HOUR_IN_MILLIS / 2,
            DateUtils.HOUR_IN_MILLIS / 4,
            DateUtils.HOUR_IN_MILLIS / 3,
        )

        fun getUserCall(startedAt: Long, durationInMs: Long): UserCall {
            return UserCall(
                startedAt = startedAt,
                endedAt = startedAt + durationInMs
            )
        }

        val calls = listOf(
            getUserCall(now - delays.random(), DateUtils.MINUTE_IN_MILLIS),
            getUserCall(yesterday - delays.random(), DateUtils.HOUR_IN_MILLIS + DateUtils.MINUTE_IN_MILLIS * 2),
            getUserCall(beforeYesterday - delays.random(), DateUtils.HOUR_IN_MILLIS / 2),
            getUserCall(lastMonth - delays.random(), DateUtils.HOUR_IN_MILLIS / 3),
            getUserCall(lastYear - delays.random(), DateUtils.HOUR_IN_MILLIS / 4),
        )

        LazyColumn {
            items(calls) {
                Spacer(Modifier.height(8.dp))

                CallHistoryItem(
                    userCall = it
                )
            }
        }
    }
}

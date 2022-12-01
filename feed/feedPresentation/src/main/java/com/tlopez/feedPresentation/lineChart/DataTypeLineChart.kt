package com.tlopez.feedPresentation.lineChart

import androidx.compose.ui.graphics.Color

enum class DataTypeLineChart(
    val color: Color,
    val string: String
) {
    ACCELERATION_X(
        Color.Red,
        "X-Axis Acceleration (cm/s2)"
    ),
    ACCELERATION_Y(
        Color.Gray,
        "Y-Axis Acceleration (cm/s2)"
    ),
    ACCELERATION_Z(
        Color.Green,
        "Z-Axis Acceleration (cm/s2)"
    ),
    BATTERY_PERCENT(
        Color.Cyan,
        "Battery Percent"
    ),
    SPEED_X(
        Color.DarkGray,
        "X-Axis Speed (dm/s)"
    ),
    SPEED_Y(
        Color.Blue,
        "Y-Axis Speed (dm/s)"
    ),
    SPEED_Z(
        Color.Magenta,
        "Z-Axis Speed (dm/s)"
    ),
    X(
        Color.Red,
        "X debug"
    ),
    Y(
        Color.Blue,
        "Y debug"
    ),
    Z(
        Color.Green,
        "Z debug"
    )
}
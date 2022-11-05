package com.tlopez.controllerPresentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

private const val BATTERY_STAGES = 7
private const val MAXIMUM_BATTERY = 100

@Composable
fun BatteryIconText(
    batteryPercentage: Int
) {
    val batteryNearestStage = (batteryPercentage * BATTERY_STAGES) / MAXIMUM_BATTERY
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector =
            Icons.Default.run {
                when (batteryNearestStage) {
                    7 -> BatteryFull
                    6 -> Battery6Bar
                    5 -> Battery5Bar
                    4 -> Battery4Bar
                    3 -> Battery3Bar
                    2 -> Battery2Bar
                    1 -> Battery1Bar
                    else -> Battery0Bar
                }
            },
            tint = Color.White,
            contentDescription = "Battery"
        )
        Text(
            text = "$batteryPercentage %",
            color = Color.White
        )
    }
}

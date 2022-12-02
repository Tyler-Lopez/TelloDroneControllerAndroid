package com.tlopez.feedPresentation.flightDetails

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.feedPresentation.flightDetails.FlightDetailsViewEvent.*
import com.tlopez.feedPresentation.lineChart.DataTypeLineChart
import com.tlopez.feedPresentation.lineChart.LineChart
import com.tlopez.feedPresentation.quadrantGraph.QuadrantGraph

@Composable
fun FlightDetailsScreen(viewModel: FlightDetailsViewModel) {
    AppScaffold(
        text = "Flight Details",
        onNavigateUp = {
            viewModel.onEventDebounced(ClickedNavigateUp)
        }
    ) {
        ScreenBackground(
            colorBackground = Color.LightGray,
            padding = 0.dp
        ) {
            viewModel.viewState.collectAsState().value?.apply {
                Text("Flight Path")
                QuadrantGraph(
                    positions = positionData.positions,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Text("Data Over Time")
                LineChart(
                    lineChartData = lineChartData,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                DataTypeLineChart.values().forEach { type ->
                    Row {
                        Checkbox(
                            checked = selectedDataTypeLineChart.contains(type),
                            onCheckedChange = {
                                viewModel.onEvent(ToggledDataTypeLineChart(type))
                            }
                        )
                        Text(type.string)
                    }
                }
            }
        }
    }
}
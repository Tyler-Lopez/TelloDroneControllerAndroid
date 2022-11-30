package com.tlopez.feedPresentation.flightDetails

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.feedPresentation.flightDetails.FlightDetailsViewEvent.*
import com.tlopez.feedPresentation.lineChart.LineChart

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
            scrollEnabled = false,
            padding = 0.dp
        ) {
            viewModel.viewState.collectAsState().value?.apply {
                LineChart(
                    lineChartData = heightLineChartData,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }
    }
}
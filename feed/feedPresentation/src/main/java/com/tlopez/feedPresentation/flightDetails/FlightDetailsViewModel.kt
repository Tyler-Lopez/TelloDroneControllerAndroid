package com.tlopez.feedPresentation.flightDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.datastoreDomain.models.TelloFlightData
import com.tlopez.datastoreDomain.usecase.GetTelloFlightData
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.FeedDestination.NavigateUp
import com.tlopez.feedPresentation.flightDetails.FlightDetailsViewEvent.ClickedNavigateUp
import com.tlopez.feedPresentation.lineChart.DataSet
import com.tlopez.feedPresentation.lineChart.LineChartData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KFunction1

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(
    private val getTelloFlightData: GetTelloFlightData,
    savedStateHandle: SavedStateHandle
) : BaseRoutingViewModel<FlightDetailsViewState, FlightDetailsViewEvent, FeedDestination>() {

    private val flightId: String = savedStateHandle["flight_id"] ?: error("Missing flight_id")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTelloFlightData(flightId)
                .doOnSuccess { flightData ->
                    FlightDetailsViewState(
                        flightData.run {
                            forEach {
                                println("Test, bat is ${it.bat}")
                                println("Test agy is ${it.agy}")
                            }
                            LineChartData(
                                dataSets = listOf(
                                    dataSetByType(TelloFlightData::getBat)
                                ),
                                rangeMaximum = maxOf(TelloFlightData::getTimeSinceStartMs).toFloat(),
                                rangeMinimum = minOf(TelloFlightData::getTimeSinceStartMs).toFloat()
                            )
                        }
                    ).push()
                }
        }
    }

    override fun onEvent(event: FlightDetailsViewEvent) {
        when (event) {
            is ClickedNavigateUp -> onClickedNavigateUp()
        }
    }

    private fun onClickedNavigateUp() {
        routeTo(NavigateUp)
    }

    private fun <R> List<TelloFlightData>.dataSetByType(type: KFunction1<TelloFlightData, R>): DataSet
            where R : Number, R : Comparable<R> = DataSet(
        dataMaximum = maxOf(type).toFloat(),
        dataMinimum = minOf(type).toFloat(),
        dataPoints = map { type(it).toFloat() to it.timeSinceStartMs.toFloat() }
    )
}
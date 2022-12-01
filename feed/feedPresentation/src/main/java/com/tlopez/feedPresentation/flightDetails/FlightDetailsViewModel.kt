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
import com.tlopez.feedPresentation.lineChart.DataTypeLineChart
import com.tlopez.feedPresentation.lineChart.DataTypeLineChart.*
import com.tlopez.feedPresentation.lineChart.LineChartData
import com.tlopez.feedPresentation.flightDetails.FlightDetailsViewEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(
    private val getTelloFlightData: GetTelloFlightData,
    savedStateHandle: SavedStateHandle
) : BaseRoutingViewModel<FlightDetailsViewState, FlightDetailsViewEvent, FeedDestination>() {

    companion object {
        private val defaultDataTypeLineChart = setOf(
            ACCELERATION_X,
            ACCELERATION_Y,
            ACCELERATION_Z
        )
    }

    private lateinit var flightData: List<TelloFlightData>
    private val flightId: String = savedStateHandle["flight_id"] ?: error("Missing flight_id")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTelloFlightData(flightId)
                .doOnSuccess {
                    flightData = it
                    FlightDetailsViewState(
                        lineChartData = flightData.run {
                            LineChartData(
                                dataSets = defaultDataTypeLineChart.map { type ->
                                    dataSetByDataType(type)
                                },
                                rangeMaximum = maxOf(TelloFlightData::getTimeSinceStartMs).toFloat(),
                                rangeMinimum = minOf(TelloFlightData::getTimeSinceStartMs).toFloat()
                            )
                        },
                        selectedDataTypeLineChart = defaultDataTypeLineChart
                    ).push()
                }
        }
    }

    override fun onEvent(event: FlightDetailsViewEvent) {
        when (event) {
            is ClickedNavigateUp -> onClickedNavigateUp()
            is ToggledDataTypeLineChart -> onToggledDataTypeLineChart(event)
        }
    }

    private fun onClickedNavigateUp() {
        routeTo(NavigateUp)
    }

    private fun onToggledDataTypeLineChart(event: ToggledDataTypeLineChart) {
        lastPushedState?.run {
            val newSelections = selectedDataTypeLineChart.toMutableSet().apply {
                if (contains(event.type)) remove(event.type) else add(event.type)
            }
            copy(
                lineChartData = lineChartData.copy(
                    dataSets = flightData.run {
                        newSelections.map {
                            dataSetByDataType(it)
                        }
                    }),
                selectedDataTypeLineChart = newSelections
            )
        }?.push()
    }

    private fun List<TelloFlightData>.dataSetByDataType(type: DataTypeLineChart): DataSet {
        val selector: (TelloFlightData) -> Double = {
            when (type) {
                ACCELERATION_X -> it.agx
                ACCELERATION_Y -> it.agy
                ACCELERATION_Z -> it.agz
                BATTERY_PERCENT -> it.bat.toDouble()
                SPEED_X -> it.vgx.toDouble()
                SPEED_Y -> it.vgy.toDouble()
                SPEED_Z -> it.vgz.toDouble()
                X -> it.x.toDouble()
                Y -> it.y.toDouble()
                Z -> it.z.toDouble()
            }
        }
        return DataSet(
            dataMaximum = maxOf(selector).toFloat(),
            dataMinimum = minOf(selector).toFloat(),
            dataPoints = map { selector(it).toFloat() to it.timeSinceStartMs.toFloat() },
            dataType = type
        )
    }
}
/*
    private fun <R> List<TelloFlightData>.dataSetByDataTypeSelector(selector: KFunction1<TelloFlightData, R>): DataSet
            where R : Number, R : Comparable<R> = DataSet(
        dataMaximum = maxOf(selector).toFloat(),
        dataMinimum = minOf(selector).toFloat(),
        dataPoints = map { selector(it).toFloat() to it.timeSinceStartMs.toFloat() }
    )
}

 */
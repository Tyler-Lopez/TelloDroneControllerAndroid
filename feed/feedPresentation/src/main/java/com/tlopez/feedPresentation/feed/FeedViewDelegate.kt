package com.tlopez.feedPresentation.feed

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.corePresentation.common.PullRefreshSpecification
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.feedPresentation.feed.FeedViewEvent.ClickedSettings
import com.tlopez.feedPresentation.feed.FeedViewState.*

@Composable
fun FeedViewDelegate(viewModel: FeedViewModel) {
    viewModel.viewState.collectAsState().value?.apply {
        AppScaffold(
            text = selectedNavigationItem.label,
            actions = {
                IconButton(onClick = { viewModel.onEventDebounced(ClickedSettings) }) {
                    Icon(Icons.Default.Settings, null)
                }
            },
            bottomBar = {
                FeedBottomBar(
                    selectedNavigationItem = selectedNavigationItem,
                    eventReceiver = viewModel
                )
            }
        ) {
            ScreenBackground(
                colorBackground = Color.LightGray,
                scrollEnabled = false,
                padding = 0.dp,
                pullRefreshSpecification = PullRefreshSpecification(
                    isRefreshing
                ) { viewModel.onEvent(FeedViewEvent.PulledRefresh) }
            ) {
                when (this@apply) {
                    is HomeViewState -> HomeScreen(
                        flightSummaries,
                        viewModel
                    )
                    is MyFlightsViewState -> MyFlightsScreen()
                }
            }
        }
    }
}
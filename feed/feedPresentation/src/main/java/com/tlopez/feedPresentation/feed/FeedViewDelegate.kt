package com.tlopez.feedPresentation.feed

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.feedPresentation.feed.FeedViewEvent.ClickedSettings

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
        }
    }
}
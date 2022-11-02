package com.tlopez.feedPresentation.feed

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.feedPresentation.NavigationItem
import com.tlopez.feedPresentation.NavigationItem.*
import com.tlopez.feedPresentation.feed.FeedViewEvent.*

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
                BottomNavigation {
                    NavigationItem.values().forEach { navItem ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = navItem.label) },
                            selected = navItem == selectedNavigationItem,
                            onClick = {
                                viewModel.onEvent(when (navItem) {
                                    FLY -> ClickedFly
                                    HOME -> ClickedHome
                                    FLIGHTS -> ClickedMyFlights
                                })
                            }
                        )
                    }
                }
            }
        ) {
        }
    }
}
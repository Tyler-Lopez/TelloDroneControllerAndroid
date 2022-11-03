package com.tlopez.feedPresentation.feed

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.core.architecture.EventReceiver
import com.tlopez.feedPresentation.NavigationItem

@Composable
fun FeedBottomBar(
    selectedNavigationItem: NavigationItem,
    eventReceiver: EventReceiver<FeedViewEvent>
) {
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
                    eventReceiver.onEvent(
                        when (navItem) {
                            NavigationItem.FLY -> FeedViewEvent.ClickedFly
                            NavigationItem.HOME -> FeedViewEvent.ClickedHome
                            NavigationItem.FLIGHTS -> FeedViewEvent.ClickedMyFlights
                        }
                    )
                }
            )
        }
    }
}
package com.tlopez.corePresentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppScaffold(
    text: String,
    selectedBottomOrdinal: Int = 0,
    onNavigateUp: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = text,
                        color = Color.White
                    )
                },
                navigationIcon = onNavigateUp?.let {
                    {
                        IconButton(onClick = it) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Settings, null)
                    }
                }
            )
        },
        content = content,
        bottomBar = {
            BottomNavigation {
                NavigationItem.values().forEachIndexed { index, navItem ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null
                            )
                        },
                        label = { Text(text = navItem.label) },
                        selected = index == selectedBottomOrdinal,
                        onClick = {

                        }
                    )
                }
            }
        }
    )

}
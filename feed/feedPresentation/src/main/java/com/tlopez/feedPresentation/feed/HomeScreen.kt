package com.tlopez.feedPresentation.feed

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.core.architecture.EventReceiver

@Composable
fun HomeScreen(viewModel: EventReceiver<FeedViewEvent>) {
    Button(onClick = { viewModel.onEvent(FeedViewEvent.TempClickedInsertChallenge)}) {
        Text("Click me")
    }
}
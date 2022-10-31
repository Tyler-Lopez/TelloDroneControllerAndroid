package com.tlopez.corePresentation.common

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    isLoading: Boolean,
    text: String,
    modifier: Modifier = Modifier.defaultMinSize(minWidth = 164.dp),
    onClicked: (() -> Unit)? = null
) {
    Button(
        enabled = !isLoading,
        modifier = modifier,
        onClick = { onClicked?.invoke() }
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(text)
        }
    }
}
package com.tlopez.corePresentation.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.tlopez.corePresentation.theme.Typography

@Composable
fun TextSuccess(value: String) {
    Text(
        style = Typography.subtitle2,
        color = Color.Green,
        textAlign = TextAlign.Center,
        text = value
    )
}
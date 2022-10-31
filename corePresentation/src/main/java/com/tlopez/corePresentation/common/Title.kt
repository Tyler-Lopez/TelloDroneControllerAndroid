package com.tlopez.corePresentation.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import com.tlopez.corePresentation.theme.Typography

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = Center
) {
    Text(
        text = text,
        textAlign = Center,
        style = Typography.h4
    )
}
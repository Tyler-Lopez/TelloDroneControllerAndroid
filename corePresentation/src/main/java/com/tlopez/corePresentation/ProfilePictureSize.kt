package com.tlopez.corePresentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tlopez.corePresentation.R

enum class ProfilePictureSize {
    MEDIUM, SMALL;

    @Composable
    fun getMinHeight(): Dp {
        return when (this) {
            MEDIUM -> 128.dp
            SMALL -> 64.dp
        }
    }
}
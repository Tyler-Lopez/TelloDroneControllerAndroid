package com.tlopez.corePresentation.common.button

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tlopez.corePresentation.R

@Composable
fun MediumEmphasisButton(
    size: ButtonSize,
    modifier: Modifier = Modifier
        .defaultMinSize(minWidth = dimensionResource(id = R.dimen.button_min_width)),
    isLoading: Boolean = false,
    text: String? = null,
    imageVector: ImageVector? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val cornerRadiusDp = dimensionResource(id = R.dimen.button_corner_radius)
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(cornerRadiusDp),
        modifier = modifier
            .defaultMinSize(
                minWidth = Dp.Unspecified,
                minHeight = size.getMinHeight()
            )
            .indication(
                remember { MutableInteractionSource() },
                rememberRipple(color = Color.Black)
            ),
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Gray,
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp
                )
            }
            text?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.button
                )
            }
            imageVector?.let {
                Icon(imageVector = imageVector, contentDescription = null)
            }
        }
    }
}

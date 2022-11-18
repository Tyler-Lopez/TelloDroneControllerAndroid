package com.tlopez.corePresentation.common.button

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.tlopez.corePresentation.R

@Composable
fun MediumEmphasisButton(
    size: ButtonSize,
    modifier: Modifier = Modifier
        .defaultMinSize(minWidth = dimensionResource(id = R.dimen.button_min_width)),
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

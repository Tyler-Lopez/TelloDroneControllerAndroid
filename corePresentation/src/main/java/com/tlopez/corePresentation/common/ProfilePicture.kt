package com.tlopez.corePresentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import androidx.compose.ui.layout.ContentScale.Companion.Inside
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.tlopez.corePresentation.R

@Composable
fun ProfilePicture(
    contentDescription: String = "Profile Picture",
    pictureUrl: String? = null,
) {
    Image(
        painter = pictureUrl?.let {
            rememberImagePainter(
                data = it,
                builder = {
                    placeholder(R.drawable.ic_avatar_profile)
                    error(R.drawable.ic_avatar_profile)
                }
            )
        } ?: run {
            painterResource(id = R.drawable.ic_avatar_profile)
        },
        contentDescription = contentDescription,
        contentScale = Crop,
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
    )
}
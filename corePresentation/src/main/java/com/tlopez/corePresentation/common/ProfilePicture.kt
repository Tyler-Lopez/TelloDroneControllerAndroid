package com.tlopez.corePresentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.tlopez.corePresentation.R
import java.io.File

@Composable
fun ProfilePicture(
    contentDescription: String = "Profile Picture",
    file: File? = null,
) {
    Image(
        painter = file?.let {
            rememberImagePainter(file)
        } ?: run {
            painterResource(id = R.drawable.ic_avatar_profile)
        },
        contentDescription = contentDescription,
        contentScale = FillBounds,
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
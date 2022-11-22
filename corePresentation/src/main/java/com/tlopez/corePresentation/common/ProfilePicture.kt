package com.tlopez.corePresentation.common

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.tlopez.corePresentation.R

@Composable
fun ProfilePicture(
    context: Context,
    contentDescription: String = "Profile Picture",
    pictureUrl: String? = null,
) {
    Box {
        Image(
            painter = painterResource(R.drawable.ic_avatar_profile),
            contentDescription = contentDescription,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
        )
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(pictureUrl)
                .memoryCacheKey(pictureUrl)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build(),
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
}
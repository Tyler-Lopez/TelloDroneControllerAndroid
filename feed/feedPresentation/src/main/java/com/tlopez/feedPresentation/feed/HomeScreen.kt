package com.tlopez.feedPresentation.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.tlopez.core.architecture.EventReceiver
import com.tlopez.corePresentation.ProfilePictureSize
import com.tlopez.corePresentation.common.FileSpecification
import com.tlopez.corePresentation.common.ProfilePicture
import com.tlopez.corePresentation.theme.Typography
import com.tlopez.feedPresentation.TelloFlightSummary
import java.io.File

@Composable
fun HomeScreen(
    flightSummaries: List<TelloFlightSummary>?,
    eventReceiver: EventReceiver<FeedViewEvent>
) {
    if (flightSummaries == null) {
        CircularProgressIndicator()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(flightSummaries.size) { index ->
                val flightSummary = flightSummaries[index]
                Column {
                    Box(
                        modifier = Modifier
                            .clickable {
                                eventReceiver.onEventDebounced(
                                    FeedViewEvent.ClickedFlightDetails(index)
                                )
                            }
                            .background(Color.White)
                            .padding(16.dp)
                            .fillMaxWidth()

                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.weight(2f),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    ProfilePicture(
                                        fileSpecification = flightSummary.profileUrl?.let {
                                            FileSpecification(
                                                fileUrl = it,
                                                fileKey = flightSummary.username
                                            )
                                        },
                                        profilePictureSize = ProfilePictureSize.SMALL
                                    )
                                    Column {
                                        Text(
                                            text = "@${flightSummary.username}",
                                            style = Typography.h5
                                        )
                                        Text(
                                            text = flightSummary.flightStarted,
                                            style = Typography.subtitle1
                                        )
                                    }
                                }
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = flightSummary.flightLength,
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                        //  Divider()
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}
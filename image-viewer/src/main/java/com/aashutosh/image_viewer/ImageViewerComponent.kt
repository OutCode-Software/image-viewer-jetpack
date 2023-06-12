package com.aashutosh.image_viewer

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageViewer(
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Fit,
) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val height =
        with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.toDp().toPx() }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.pointerInput(Unit) {
            forEachGesture {
                awaitPointerEventScope {
                    awaitFirstDown()
                    do {
                        val event = awaitPointerEvent()
                        scale = (scale * event.calculateZoom()).coerceIn(1f, 3f)
                        val offset = event.calculatePan()
                        offsetX = (offsetX + offset.x)
                            .coerceIn(
                                -((scale - 1f).coerceIn(
                                    0F,
                                    1F
                                ) * (size.width.toFloat() * .33F) * scale),
                                ((scale - 1F).coerceIn(
                                    0F,
                                    1F
                                ) * (size.width.toFloat() * .33F) * scale)
                            )
                        offsetY = (offsetY + offset.y)
                            .coerceIn(
                                -((scale - 1F).coerceIn(0F, 1F) * (height * .33F) * scale),
                                ((scale - 1F).coerceIn(0F, 1F) * (height * .33F) * scale)
                            )
                    } while (event.changes.any { it.pressed })
                }
            }
        }

    ) {
        GlideImage(
            model = imageUrl,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                ),
            contentScale = contentScale,
        )
    }
}
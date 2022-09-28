package com.example.stocktest.presentation.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import timber.log.Timber

/**
 * o : open
 * c : close
 * l : low
 * h : high
 */
@Composable
fun CandleView(color: Color, c: Int, o: Int, l: Int, h: Int) {
    val tag = "CandleView-bob"
    Timber.tag(tag).d("DrawCandle() = c : $c, o = $o, l : $l, h : $h")

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(Color.White)

    ) {
        Canvas(
            modifier = Modifier
                .height(40.dp)
                .width(30.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val strokeWidth = 5f

            drawLine(
                color = color,
                start = Offset((canvasWidth+strokeWidth)/2, 0f),
                end = Offset((canvasWidth+strokeWidth)/2, canvasHeight),
                strokeWidth = strokeWidth
            )

            val rectTop = (Integer.max(o, c).toFloat() - l) / (h - l)
            val rectBottom = (Integer.min(o, c).toFloat() - l) / (h - l)

            Timber.tag(tag).d("DrawCandle(1) = rectTop : $rectTop, rectBottom = $rectBottom")

            drawRect (
                color = color,
                topLeft = Offset(x = (canvasWidth - (size.width / 2F)) / 2, y = (size.height - (size.height * rectTop))),
                size = Size(width = size.width / 2F, height = size.height * (rectTop - rectBottom))
            )
        }
    }
}
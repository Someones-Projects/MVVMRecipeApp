package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.max
import kotlin.math.min


@Composable
fun CollapsingImageLayout(
    collapseFraction: Float,
    modifier: Modifier = Modifier,
    expandedImageSize: Dp = 300.dp,
    collapsedImageSize: Dp = 150.dp,
    image: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        children = image
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val imageMaxSize = min(expandedImageSize.toIntPx(), constraints.maxWidth)
        val imageMinSize = max(collapsedImageSize.toIntPx(), constraints.minWidth)
        val imageWidth = constraints.maxWidth
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(imageMaxSize, imageMinSize, collapseFraction)

        layout(
            width = constraints.maxWidth,
            height = imageY
        ) {
            imagePlaceable.place(constraints.maxWidth, imageY)
        }
    }
}



















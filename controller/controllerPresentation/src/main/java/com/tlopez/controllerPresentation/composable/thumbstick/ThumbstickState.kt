package com.tlopez.controllerPresentation.composable.thumbstick

data class ThumbstickState(
    val fractionHorizontal: Float = 0f,
    val fractionVertical: Float = 0f
) {
    companion object {
        private val ACCEPTABLE_RANGE = -1f..1f
    }

    fun copyWithPercentAdjustment(adjX: Float, adjY: Float): ThumbstickState {
        return copy(
            fractionHorizontal = (fractionHorizontal + adjX)
                .coerceIn(ACCEPTABLE_RANGE),
            fractionVertical = (fractionVertical + adjY)
                .coerceIn(ACCEPTABLE_RANGE)
        )
    }
}

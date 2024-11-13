package az.lahza.shimmer_animation.shimmer.annotations

import androidx.annotation.IntDef

/**
 * Annotation for defining the direction of shimmer animation.
 * The direction can be one of the following:
 * - LEFT_TO_RIGHT
 * - TOP_TO_BOTTOM
 * - RIGHT_TO_LEFT
 * - BOTTOM_TO_TOP
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    Direction.LEFT_TO_RIGHT,
    Direction.TOP_TO_BOTTOM,
    Direction.RIGHT_TO_LEFT,
    Direction.BOTTOM_TO_TOP
)
annotation class Direction {

    companion object {
        const val LEFT_TO_RIGHT = 0
        const val TOP_TO_BOTTOM = 1
        const val RIGHT_TO_LEFT = 2
        const val BOTTOM_TO_TOP = 3
    }
}

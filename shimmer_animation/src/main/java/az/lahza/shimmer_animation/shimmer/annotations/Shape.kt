package az.lahza.shimmer_animation.shimmer.annotations

import androidx.annotation.IntDef

/**
 * Annotation class for defining the type of shimmer effect shape.
 *
 * Supported shapes are:
 * - LINEAR: A linear shimmer effect.
 * - RADIAL: A radial shimmer effect.
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(Shape.LINEAR, Shape.RADIAL)
annotation class Shape {

    companion object {
        // Constant for a linear shimmer effect
        const val LINEAR = 0

        // Constant for a radial shimmer effect
        const val RADIAL = 1
    }
}

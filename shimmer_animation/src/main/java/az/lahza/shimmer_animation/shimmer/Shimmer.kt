package az.lahza.shimmer_animation.shimmer

import android.animation.ValueAnimator
import android.graphics.Color
import androidx.annotation.ColorInt
import az.lahza.shimmer_animation.shimmer.annotations.Direction
import az.lahza.shimmer_animation.shimmer.annotations.Shape
import kotlin.math.roundToInt

/**
 * A class that defines the shimmer animation properties and behavior.
 */
class Shimmer {

    // ============================
    // Shimmer Configuration
    // ============================

    /** Direction of the shimmer animation */
    @Direction var direction = Direction.LEFT_TO_RIGHT

    /** Highlight color of the shimmer effect */
    @ColorInt var highlightColor = Color.WHITE

    /** Base color of the shimmer effect */
    @ColorInt var baseColor = 0x4cffffff

    /** Shape of the shimmer animation */
    @Shape var shape = Shape.LINEAR

    /** Fixed width of the shimmer (0 for dynamic) */
    private var fixedWidth = 0

    /** Fixed height of the shimmer (0 for dynamic) */
    private var fixedHeight = 0

    /** Width ratio relative to the view's width */
    var widthRatio = 1f

    /** Height ratio relative to the view's height */
    var heightRatio = 1f

    /** Intensity of the shimmer effect */
    var intensity = 0f

    /** Dropoff effect for shimmer (controls fadeout) */
    var dropoff = 0.5f

    /** Tilt angle for shimmer movement */
    var tilt = 20f

    /** Whether to clip the shimmer to the children views */
    var clipToChildren = true

    /** Whether the shimmer effect should auto-start */
    var autoStart = true

    /** Whether the shimmer effect includes alpha shimmer */
    var alphaShimmer = true

    /** Number of repetitions for the shimmer effect */
    var repeatCount = ValueAnimator.INFINITE

    /** The repeat mode of the shimmer animation */
    var repeatMode = ValueAnimator.RESTART

    /** Duration of the shimmer animation */
    var animationDuration = 1000L

    /** Delay before the shimmer effect starts */
    var repeatDelay: Long = 0

    /** Delay before the shimmer effect begins animating */
    var startDelay: Long = 0

    // ============================
    // Shimmer Effect Arrays
    // ============================

    /** Array of shimmer effect positions */
    val positions = FloatArray(COMPONENT_COUNT)

    /** Array of shimmer effect colors */
    val colors = IntArray(COMPONENT_COUNT)

    // ============================
    // Width and Height Calculations
    // ============================

    /**
     * Calculates the shimmer width based on fixedWidth or widthRatio.
     */
    fun width(width: Int) = if (fixedWidth > 0) fixedWidth else (widthRatio * width).roundToInt()

    /**
     * Calculates the shimmer height based on fixedHeight or heightRatio.
     */
    fun height(height: Int) = if (fixedHeight > 0) fixedHeight else (heightRatio * height).roundToInt()

    // ============================
    // Update Shimmer Colors
    // ============================

    /**
     * Updates the shimmer colors based on the shape of the shimmer effect.
     */
    fun updateColors() {
        colors.apply {
            val colorSequence = when (shape) {
                Shape.LINEAR -> listOf(baseColor, highlightColor, highlightColor, baseColor)
                Shape.RADIAL -> listOf(highlightColor, highlightColor, baseColor, baseColor)
                else -> listOf(baseColor, highlightColor, highlightColor, baseColor)
            }
            colorSequence.forEachIndexed { index, color -> this[index] = color }
        }
    }

    // ============================
    // Update Shimmer Positions
    // ============================

    /**
     * Updates the shimmer positions based on intensity and dropOff.
     */
    fun updatePositions() {
        val halfIntensity = (1f - intensity - dropoff) / 2f
        val halfIntensityEdge = (1f - intensity - 0.001f) / 2f
        val halfEndEdge = (1f + intensity + 0.001f) / 2f
        val halfEnd = (1f + intensity + dropoff) / 2f

        // Ensure values are within valid bounds (0f to 1f)
        val startPosition = halfIntensity.coerceAtLeast(0f)
        val startEdgePosition = halfIntensityEdge.coerceAtLeast(0f)
        val endEdgePosition = halfEndEdge.coerceAtMost(1f)
        val endPosition = halfEnd.coerceAtMost(1f)

        // Update the shimmer gradient positions
        positions.apply {
            this[0] = startPosition
            this[1] = startEdgePosition
            this[2] = endEdgePosition
            this[3] = endPosition
        }
    }

    companion object {
        /** The number of components in the shimmer effect */
        private const val COMPONENT_COUNT = 4
    }
}

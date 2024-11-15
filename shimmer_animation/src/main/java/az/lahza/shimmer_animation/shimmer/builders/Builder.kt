package az.lahza.shimmer_animation.shimmer.builders

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import az.lahza.shimmer_animation.R
import az.lahza.shimmer_animation.shimmer.Shimmer
import az.lahza.shimmer_animation.shimmer.annotations.Direction
import az.lahza.shimmer_animation.shimmer.annotations.Shape
import az.lahza.shimmer_animation.shimmer.extensions.getBooleanOrNull
import az.lahza.shimmer_animation.shimmer.extensions.getFloatOrNull
import az.lahza.shimmer_animation.shimmer.extensions.getIntOrNull

abstract class Builder<T : Builder<T>?> {

    protected val shimmer = Shimmer()

    protected abstract val self: T

    /**
     * Builds the final Shimmer instance with applied attributes.
     */
    fun build() = shimmer.apply {
        updateColors()
        updatePositions()
    }

    /**
     * Loads custom attributes for a view from XML.
     *
     * This method retrieves the custom attributes defined in the XML layout
     * and applies them to the shimmer effect configuration.
     *
     * @param context The context used to obtain styled attributes.
     * @param attrs The attribute set from the XML layout.
     * @return The builder instance for method chaining.
     */
    fun loadAttributes(context: Context, attrs: AttributeSet?): T {
        context.obtainStyledAttributes(
            attrs, R.styleable.ShimmerFrameLayout
        ).use { typedArray ->
            loadAttributes(typedArray)
        }
        return self
    }

    /**
     * Loads custom attributes from a TypedArray and applies them to the shimmer object.
     *
     * @param typedArray The TypedArray containing the custom attributes.
     * @return The builder instance for method chaining.
     */
    open fun loadAttributes(typedArray: TypedArray): T {
        typedArray.apply {
            getBooleanOrNull(R.styleable.ShimmerFrameLayout_shimmer_clip_to_children)?.let { shimmer.clipToChildren = it }
            getBooleanOrNull(R.styleable.ShimmerFrameLayout_shimmer_auto_start)?.let { shimmer.autoStart = it }
            getFloatOrNull(R.styleable.ShimmerFrameLayout_shimmer_base_alpha)?.let { setBaseAlpha(it) }
            getFloatOrNull(R.styleable.ShimmerFrameLayout_shimmer_highlight_alpha)?.let { setHighlightAlpha(it) }
            getFloatOrNull(R.styleable.ShimmerFrameLayout_shimmer_intensity)?.let { setIntensity(it) }
            getFloatOrNull(R.styleable.ShimmerFrameLayout_shimmer_width_ratio)?.let { setWidthRatio(it) }
            getFloatOrNull(R.styleable.ShimmerFrameLayout_shimmer_height_ratio)?.let { setHeightRatio(it) }
            getFloatOrNull(R.styleable.ShimmerFrameLayout_shimmer_dropoff)?.let { setDropOff(it) }
            getFloatOrNull(R.styleable.ShimmerFrameLayout_shimmer_tilt)?.let { shimmer.tilt = it }
            getIntOrNull(R.styleable.ShimmerFrameLayout_shimmer_direction)?.let { setDirection(it) }
            getIntOrNull(R.styleable.ShimmerFrameLayout_shimmer_shape)?.let { setShape(it) }
            getIntOrNull(R.styleable.ShimmerFrameLayout_shimmer_duration)?.let { shimmer.animationDuration = it.toLong() }
            getIntOrNull(R.styleable.ShimmerFrameLayout_shimmer_repeat_count)?.let { shimmer.repeatCount = it }
            getIntOrNull(R.styleable.ShimmerFrameLayout_shimmer_repeat_delay)?.let { shimmer.repeatDelay = it.toLong() }
            getIntOrNull(R.styleable.ShimmerFrameLayout_shimmer_start_delay)?.let { shimmer.startDelay = it.toLong() }
        }
        return self
    }

    // ===========================
    // Setter Methods for Shimmer Properties
    // ===========================

    /**
     * Sets the base alpha value for the shimmer effect.
     */
    private fun setBaseAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) = apply {
        shimmer.baseColor = adjustAlpha(shimmer.baseColor, alpha)
    }

    /**
     * Sets the highlight alpha value for the shimmer effect.
     */
    private fun setHighlightAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) = apply {
        shimmer.highlightColor = adjustAlpha(shimmer.highlightColor, alpha)
    }

    /**
     * Adjusts the alpha value of a color by the given factor.
     * The factor should be between 0.0 and 1.0.
     */
    private fun adjustAlpha(@ColorInt color: Int, factor: Float): Int {
        val alpha = (255 * factor).toInt()
        return (color and 0x00FFFFFF) or (alpha shl 24)
    }

    /**
     * Sets the intensity for the shimmer effect.
     * Intensity controls how strong the shimmer effect appears.
     *
     * @param intensity The intensity value, typically between 0.0 and 1.0.
     */
    private fun setIntensity(intensity: Float) = apply {
        shimmer.intensity = intensity
    }

    /**
     * Sets the width ratio for the shimmer effect.
     * Width ratio controls how wide the shimmer effect appears relative to the view's width.
     *
     * @param widthRatio The width ratio, typically between 0.0 and 1.0.
     */
    private fun setWidthRatio(widthRatio: Float) = apply {
        shimmer.widthRatio = widthRatio
    }

    /**
     * Sets the height ratio for the shimmer effect.
     * Height ratio controls how tall the shimmer effect appears relative to the view's height.
     *
     * @param heightRatio The height ratio, typically between 0.0 and 1.0.
     */
    private fun setHeightRatio(heightRatio: Float) = apply {
        shimmer.heightRatio = heightRatio
    }

    /**
     * Sets the dropOff value for the shimmer effect.
     * DropOff controls how quickly the shimmer effect fades off.
     *
     * @param dropOff The dropOff value, typically between 0.0 and 1.0.
     */
    private fun setDropOff(dropOff: Float) = apply {
        shimmer.dropoff = dropOff
    }

    /**
     * Sets the direction of the shimmer effect.
     * Direction defines in which direction the shimmer animation occurs.
     *
     * @param direction One of the predefined direction constants from the [Direction] annotation.
     */
    private fun setDirection(@Direction direction: Int) = apply {
        shimmer.direction = direction
    }

    /**
     * Sets the shape of the shimmer effect.
     * Shape defines the pattern or geometry of the shimmer effect.
     *
     * @param shape One of the predefined shape constants from the [Shape] annotation.
     */
    private fun setShape(@Shape shape: Int) = apply {
        shimmer.shape = shape
    }
}
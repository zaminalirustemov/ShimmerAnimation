package az.lahza.shimmer_animation.shimmer.builders

import android.content.res.TypedArray
import androidx.annotation.ColorInt
import az.lahza.shimmer_animation.R
import az.lahza.shimmer_animation.shimmer.extensions.getColorOrNull

/**
 * Builder class for configuring color highlight settings for shimmer animation.
 * This builder allows setting both the base color and highlight color of the shimmer effect.
 */
class ColorHighlightBuilder : Builder<ColorHighlightBuilder>() {

    /**
     * Returns the current instance of ColorHighlightBuilder for method chaining.
     */
    override val self: ColorHighlightBuilder get() = this

    init {
        // Disable alpha shimmer by default
        shimmer.alphaShimmer = false
    }

    /**
     * Sets the highlight color for the shimmer effect.
     *
     * @param color The color to be set as the highlight color.
     */
    private fun setHighlightColor(@ColorInt color: Int) = apply {
        shimmer.highlightColor = color
    }

    /**
     * Sets the base color for the shimmer effect.
     * This method modifies the base color while keeping the alpha channel intact.
     *
     * @param color The color to be set as the base color.
     */
    private fun setBaseColor(@ColorInt color: Int) = apply {
        shimmer.baseColor = (shimmer.baseColor and -0x1000000) or (color and 0x00FFFFFF)
    }

    /**
     * Loads attributes from a TypedArray and applies the color values.
     *
     * @param typedArray The TypedArray containing the attribute values.
     * @return The builder instance for method chaining.
     */
    override fun loadAttributes(typedArray: TypedArray): ColorHighlightBuilder {
        super.loadAttributes(typedArray)

        // Get the base color and highlight color from the attributes and apply them
        typedArray.getColorOrNull(R.styleable.ShimmerFrameLayout_shimmer_base_color)?.let { setBaseColor(it) }
        typedArray.getColorOrNull(R.styleable.ShimmerFrameLayout_shimmer_highlight_color)?.let { setHighlightColor(it) }

        return this
    }
}

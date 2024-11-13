package az.lahza.shimmer_animation.shimmer.builders

/**
 * A builder class for creating an alpha highlight shimmer effect.
 *
 * This builder enables the alpha shimmer effect and allows further customization.
 */
class AlphaHighlightBuilder : Builder<AlphaHighlightBuilder>() {

    /**
     *Return the current instance of AlphaHighlightBuilder for method chaining
     */
    override val self: AlphaHighlightBuilder
        get() = this

    init {
        // Enable the alpha shimmer effect by default
        shimmer.alphaShimmer = true
    }
}

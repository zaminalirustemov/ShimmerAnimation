package az.lahza.shimmer_animation.shimmer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import az.lahza.shimmer_animation.R
import az.lahza.shimmer_animation.shimmer.builders.AlphaHighlightBuilder
import az.lahza.shimmer_animation.shimmer.builders.ColorHighlightBuilder

/**
 * A custom FrameLayout that displays a shimmer effect over its content.
 * The shimmer effect can be customized using XML attributes.
 */
class ShimmerFrameLayout : FrameLayout {

    // Paint object for content rendering
    private val mContentPaint = Paint()

    // Shimmer drawable that applies the shimmer effect
    private val mShimmerDrawable: ShimmerDrawable = ShimmerDrawable()

    // Flag to track if shimmer is visible
    private var isShimmerVisible = true

    // Flag to track if shimmer should be stopped because of visibility change
    private var mStoppedShimmerBecauseVisibility = false

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    /**
     * Initializes the shimmer effect based on XML attributes.
     * If no attributes are provided, the default AlphaHighlightBuilder is used.
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        setWillNotDraw(false) // Allow drawing the shimmer effect
        mShimmerDrawable.callback = this // Set the callback for drawing the shimmer effect

        // Set default shimmer if no attributes are provided
        if (attrs == null) {
            setShimmer(AlphaHighlightBuilder().build())
            return
        }

        // Load shimmer attributes from XML
        val typedArray = context.obtainStyledAttributes(
            /* set = */          attrs,
            /* attrs = */        R.styleable.ShimmerFrameLayout,
            /* defStyleAttr = */ 0,
            /* defStyleRes = */  0
        )
        try {
            val shimmerBuilder =
                if (typedArray.hasValue(R.styleable.ShimmerFrameLayout_shimmer_colored)
                    && typedArray.getBoolean(R.styleable.ShimmerFrameLayout_shimmer_colored, false)
                ) ColorHighlightBuilder() else AlphaHighlightBuilder()

            // Set shimmer properties from XML attributes
            setShimmer(shimmerBuilder.loadAttributes(typedArray)!!.build())
        } finally {
            typedArray.recycle()
        }
    }

    /**
     * Sets the shimmer effect to be displayed.
     * If the shimmer requires clipping to children, set hardware acceleration.
     */
    private fun setShimmer(shimmer: Shimmer?): ShimmerFrameLayout {
        mShimmerDrawable.shimmer = shimmer
        if (shimmer != null && shimmer.clipToChildren) {
            setLayerType(LAYER_TYPE_HARDWARE, mContentPaint)
        } else {
            setLayerType(LAYER_TYPE_NONE, null)
        }
        return this
    }

    /**
     * Stops the shimmer animation.
     */
    private fun stopShimmer() {
        mStoppedShimmerBecauseVisibility = false
        mShimmerDrawable.stopShimmer()
    }

    /**
     * Checks if the shimmer animation has started.
     */
    private val isShimmerStarted: Boolean
        get() = mShimmerDrawable.isShimmerStarted

    /**
     * Called when the layout bounds change. Updates the shimmer bounds accordingly.
     */
    public override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        val width = width
        val height = height
        mShimmerDrawable.setBounds(0, 0, width, height)
    }

    /**
     * Handles visibility changes. Stops the shimmer if visibility is not VISIBLE,
     * and restarts it if visibility is restored.
     */
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)

        if (visibility != VISIBLE) {
            if (isShimmerStarted) {
                stopShimmer()
                mStoppedShimmerBecauseVisibility = true
            }
        } else if (mStoppedShimmerBecauseVisibility) {
            mShimmerDrawable.maybeStartShimmer()
            mStoppedShimmerBecauseVisibility = false
        }
    }

    /**
     * Called when the view is attached to the window. Starts the shimmer animation if needed.
     */
    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mShimmerDrawable.maybeStartShimmer()
    }

    /**
     * Called when the view is detached from the window. Stops the shimmer animation.
     */
    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopShimmer()
    }

    /**
     * Draws the content of the layout along with the shimmer effect.
     */
    public override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (isShimmerVisible) {
            mShimmerDrawable.draw(canvas)
        }
    }

    /**
     * Verifies if the drawable is the shimmer drawable.
     */
    override fun verifyDrawable(who: Drawable) =
        super.verifyDrawable(who) || who === mShimmerDrawable
}

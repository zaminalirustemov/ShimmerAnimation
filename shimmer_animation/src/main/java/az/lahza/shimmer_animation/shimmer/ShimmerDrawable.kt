package az.lahza.shimmer_animation.shimmer

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator
import az.lahza.shimmer_animation.shimmer.annotations.Direction
import az.lahza.shimmer_animation.shimmer.annotations.Shape
import kotlin.math.sqrt
import kotlin.math.tan

/**
 * A drawable used to display a shimmer effect on a canvas.
 *
 * This drawable applies a shimmer effect over a view by animating a gradient
 * across the surface. The shimmer can be configured with various properties
 * such as direction, shape, tilt, intensity, and more.
 */
class ShimmerDrawable : Drawable() {

    // Animator listener to trigger a redraw on each animation frame
    private val mUpdateListener = AnimatorUpdateListener { invalidateSelf() }

    // Paint object used to draw the shimmer
    private val mShimmerPaint = Paint().apply { isAntiAlias = true }

    // Rect object to define the bounds for drawing
    private val mDrawRect = Rect()

    // Matrix for manipulating the shader
    private val mShaderMatrix = Matrix()

    // ValueAnimator for controlling the shimmer animation
    private var mValueAnimator: ValueAnimator? = null

    // Used for static progress if animation is not running
    private var mStaticAnimationProgress = -1f

    // Holds the shimmer configuration
    private var mShimmer: Shimmer? = null

    /**
     * The shimmer configuration that defines the behavior of the shimmer effect.
     * Setting this property triggers the shimmer update process.
     */
    var shimmer: Shimmer?
        get() = mShimmer
        set(value) {
            mShimmer = value
            mShimmer?.let {
                mShimmerPaint.xfermode = PorterDuffXfermode(
                    if (it.alphaShimmer) PorterDuff.Mode.DST_IN else PorterDuff.Mode.SRC_IN
                )
                updateShader()
                updateValueAnimator()
                invalidateSelf()
            }
        }

    /**
     * Indicates whether the shimmer animation has started.
     */
    val isShimmerStarted: Boolean
        get() = mValueAnimator?.isStarted == true

    /**
     * Stops the shimmer animation if it is currently running.
     */
    fun stopShimmer() {
        if (mValueAnimator != null && isShimmerStarted) {
            mValueAnimator!!.cancel()
        }
    }

    /**
     * Called when the bounds of the drawable change.
     * This updates the drawing rectangle and shader, and potentially starts the shimmer animation.
     */
    public override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        mDrawRect.set(bounds)
        updateShader()
        maybeStartShimmer()
    }

    /**
     * Draws the shimmer effect on the canvas. The shimmer is animated by applying a gradient
     * shader and translating it according to the animation progress.
     *
     * @param canvas The canvas on which to draw the shimmer effect.
     */
    override fun draw(canvas: Canvas) {
        val shimmer = mShimmer ?: return
        val shader = mShimmerPaint.shader ?: return

        // Calculate the tilt factor based on the shimmer tilt angle
        val tiltTan = tan(Math.toRadians(shimmer.tilt.toDouble())).toFloat()

        // Calculate translation values based on the shimmer tilt and bounds
        val translateHeight = mDrawRect.height() + tiltTan * mDrawRect.width()
        val translateWidth = mDrawRect.width() + tiltTan * mDrawRect.height()

        // Determine the animation progress (either static or from the animator)
        val animatedValue =
            mStaticAnimationProgress.takeIf { it >= 0f } ?: (mValueAnimator?.animatedValue as? Float
                ?: 0f)

        // Calculate the translation offsets based on the shimmer direction
        val (dx, dy) = calculateTranslation(
            shimmer.direction, translateWidth, translateHeight, animatedValue
        )

        // Reset and update the shader matrix for applying translation and rotation
        mShaderMatrix.reset()
        mShaderMatrix.setRotate(shimmer.tilt, mDrawRect.width() / 2f, mDrawRect.height() / 2f)
        mShaderMatrix.preTranslate(dx, dy)

        // Apply the transformation to the shader and draw the shimmer effect
        shader.setLocalMatrix(mShaderMatrix)
        canvas.drawRect(mDrawRect, mShimmerPaint)
    }


    /**
     * Calculates the translation offsets (dx, dy) based on the shimmer direction.
     *
     * @param direction The direction of the shimmer effect.
     * @param translateWidth The width translation value.
     * @param translateHeight The height translation value.
     * @param animatedValue The current progress of the animation.
     * @return A pair of translation values (dx, dy).
     */
    private fun calculateTranslation(
        direction: Int, translateWidth: Float, translateHeight: Float, animatedValue: Float
    ) = when (direction) {
        Direction.LEFT_TO_RIGHT -> offset(-translateWidth, translateWidth, animatedValue) to 0f
        Direction.RIGHT_TO_LEFT -> offset(translateWidth, -translateWidth, animatedValue) to 0f
        Direction.TOP_TO_BOTTOM -> 0f to offset(-translateHeight, translateHeight, animatedValue)
        Direction.BOTTOM_TO_TOP -> 0f to offset(translateHeight, -translateHeight, animatedValue)
        else -> offset(-translateWidth, translateWidth, animatedValue) to 0f
    }


    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return if (mShimmer?.clipToChildren == true || mShimmer?.alphaShimmer == true) {
            PixelFormat.TRANSLUCENT
        } else {
            PixelFormat.OPAQUE
        }
    }

    /**
     * Adjusts the translation offset based on the animation progress.
     *
     * @param start The starting translation value.
     * @param end The ending translation value.
     * @param percent The progress of the animation (0f to 1f).
     * @return The calculated offset.
     */
    private fun offset(start: Float, end: Float, percent: Float) = start + (end - start) * percent

    /**
     * Updates the ValueAnimator for the shimmer animation based on the current shimmer properties.
     * Cancels the existing animator (if any) and creates a new one with the correct properties.
     */
    private fun updateValueAnimator() {
        // Ensure shimmer is not null
        mShimmer?.let { shimmer ->
            // Cancel and reset the existing animator, if it exists
            mValueAnimator?.cancel()
            mValueAnimator?.removeAllUpdateListeners()

            // Create a new ValueAnimator with the correct properties
            mValueAnimator = ValueAnimator.ofFloat(
                0f, 1f + (shimmer.repeatDelay / shimmer.animationDuration).toFloat()
            ).apply {
                // Set the interpolator for the animation
                interpolator = LinearInterpolator()

                // Set repeat mode, count, and duration based on shimmer properties
                repeatMode = shimmer.repeatMode
                startDelay = shimmer.startDelay
                repeatCount = shimmer.repeatCount
                duration = shimmer.animationDuration + shimmer.repeatDelay

                // Add an update listener to trigger invalidation
                addUpdateListener(mUpdateListener)

                // Start the animation
                start()
            }
        }
    }

    /**
     * Starts the shimmer animation if it is not already started and the conditions are met.
     */
    fun maybeStartShimmer() {
        // Ensure the shimmer and animator are valid, and autoStart is true
        if (mValueAnimator?.isStarted == true || mShimmer?.autoStart == false || callback == null) return

        // Start the shimmer animation if the conditions are satisfied
        mValueAnimator?.start()
    }


    /**
     * Updates the shader used for drawing the shimmer effect.
     * This method creates the shader based on the shimmer properties.
     */
    private fun updateShader() {
        // Early return if shimmer is null or bounds are invalid
        val shimmer = mShimmer ?: return
        val bounds = bounds
        if (bounds.width() == 0 || bounds.height() == 0) return

        // Calculate the width and height for the shimmer effect
        val width = shimmer.width(bounds.width())
        val height = shimmer.height(bounds.height())

        // Create the appropriate shader based on shimmer properties
        val shader = createShader(shimmer, width, height)

        // Apply the shader to the paint object
        mShimmerPaint.shader = shader
    }

    /**
     * Creates the shader for the shimmer effect based on the current shimmer properties.
     *
     * @param width The width of the shimmer effect.
     * @param height The height of the shimmer effect.
     * @return The shader to be used for the shimmer effect.
     */
    private fun createShader(shimmer: Shimmer, width: Int, height: Int): Shader {
        return when (shimmer.shape) {
            Shape.LINEAR -> createLinearGradient(shimmer, width, height)
            Shape.RADIAL -> createRadialGradient(shimmer, width, height)
            else -> createLinearGradient(shimmer, width, height) // Default to linear gradient
        }
    }

    /**
     * Creates a linear gradient shader for the shimmer effect based on the shimmer direction.
     *
     * @param shimmer The shimmer configuration containing direction, colors, and positions.
     * @param width The width of the shimmer effect (view width).
     * @param height The height of the shimmer effect (view height).
     * @return The linear gradient shader based on the shimmer's direction and properties.
     */
    private fun createLinearGradient(shimmer: Shimmer, width: Int, height: Int): LinearGradient {
        // Determine whether the shimmer direction is vertical or horizontal
        val isVertical =
            shimmer.direction == Direction.TOP_TO_BOTTOM || shimmer.direction == Direction.BOTTOM_TO_TOP

        // Calculate the end coordinates for the gradient based on the direction
        val endX = if (isVertical) 0 else width
        val endY = if (isVertical) height else 0

        // Return the linear gradient shader with the calculated values
        return LinearGradient(
            0f, // Start at the top-left corner (0,0)
            0f, // Start at the top-left corner (0,0)
            endX.toFloat(), endY.toFloat(), shimmer.colors,   // Color stops
            shimmer.positions, // Gradient positions
            Shader.TileMode.CLAMP // Prevents the gradient from repeating
        )
    }


    /**
     * Creates a radial gradient shader for the shimmer effect.
     *
     * @param shimmer The shimmer configuration.
     * @param width The width of the shimmer effect.
     * @param height The height of the shimmer effect.
     * @return The radial gradient shader.
     */
    private fun createRadialGradient(shimmer: Shimmer, width: Int, height: Int) =
        RadialGradient(
            width / 2f,
            height / 2f,
            (width.coerceAtLeast(height) / sqrt(2.0)).toFloat(),
            shimmer.colors,
            shimmer.positions,
            Shader.TileMode.CLAMP
        )
}
package az.lahza.shimmer_animation.shimmer.extensions

import android.content.res.TypedArray

/**
 * Extension function to retrieve a boolean value from a TypedArray.
 * Returns null if the attribute does not exist.
 *
 * @param index The index of the attribute.
 * @return The boolean value or null if not present.
 */
fun TypedArray.getBooleanOrNull(index: Int): Boolean? =
    if (hasValue(index)) getBoolean(index, false) else null

/**
 * Extension function to retrieve a float value from a TypedArray.
 * Returns null if the attribute does not exist.
 *
 * @param index The index of the attribute.
 * @return The float value or null if not present.
 */
fun TypedArray.getFloatOrNull(index: Int): Float? =
    if (hasValue(index)) getFloat(index, 0f) else null

/**
 * Extension function to retrieve an integer value from a TypedArray.
 * Returns null if the attribute does not exist.
 *
 * @param index The index of the attribute.
 * @return The integer value or null if not present.
 */
fun TypedArray.getIntOrNull(index: Int): Int? =
    if (hasValue(index)) getInt(index, 0) else null

/**
 * Extension function to retrieve a color value from a TypedArray.
 * Returns null if the attribute does not exist.
 *
 * @param index The index of the attribute.
 * @return The color value or null if not present.
 */
fun TypedArray.getColorOrNull(index: Int): Int? =
    if (hasValue(index)) getColor(index, 0) else null

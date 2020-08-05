package com.example.holmi_production.money_counter_app.ui.utils

import android.content.res.Resources

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */

/**
 * Convert Dp to Px value
 */
fun dpToPx(dp: Int): Float {
    return (dp * Resources.getSystem().displayMetrics.density)
}
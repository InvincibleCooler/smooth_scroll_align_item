package me.invin.anchorposition.utils

import android.content.Context


fun dipToPixel(context: Context?, dip: Float): Int {
    context?.let {
        val density = it.resources.displayMetrics.density
        return (dip * density).toInt()
    }
    return 0
}
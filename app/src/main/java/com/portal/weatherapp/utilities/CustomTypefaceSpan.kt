package com.portal.weatherapp.utilities

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class CustomTypefaceSpan(type: Typeface?) : TypefaceSpan("") {
    private var newType: Typeface? = type

    override fun updateDrawState(drawState: TextPaint) {
        applyCustomTypeFace(drawState, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    private fun applyCustomTypeFace(paint: Paint, typeFace: Typeface?) {
        val oldStyle: Int
        val old = paint.typeface
        oldStyle = old?.style ?: 0
        val fake = oldStyle and typeFace!!.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }
        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }
        paint.typeface = typeFace
    }
}
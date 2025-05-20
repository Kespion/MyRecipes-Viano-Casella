package com.ynov.myrecipes.ui.detail

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class NutriScoreGauge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var percent: Float by Delegates.observable(0f) { _, _, _ -> invalidate() }

    private val stroke = 40f
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = stroke
        color = Color.parseColor("#33000000")
    }
    private val fgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = stroke
    }
    private val arc = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val pad = stroke / 2 + 4f
        arc.set(pad, pad, w - pad, h - pad)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawArc(arc, 180f, 180f, false, bgPaint)

        fgPaint.color = Color.HSVToColor(
            floatArrayOf(percent.coerceIn(0f, 100f) * 1.2f, 1f, 1f)
        )

        canvas.drawArc(arc, 180f, percent * 1.8f, false, fgPaint)
    }
}
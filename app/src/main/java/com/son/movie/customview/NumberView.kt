package com.son.movie.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.son.movie.R

//class NumberView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet?,
//    defStyleAttr: Int = 0
//) : View(context, attrs, defStyleAttr) {
//    private var text: String = "1"
//    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        style = Paint.Style.FILL
//        color = context.getColor(R.color.dark_navy_blue)
//        textSize = 120f
//    }
//    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        style = Paint.Style.STROKE
//        strokeWidth = 1f
//        textSize = 120f
//        color = context.getColor(R.color.fresh_blue)
//    }
//
//    init {
//        context.withStyledAttributes(attrs, R.styleable.NumberView) {
//            text = getString(R.styleable.NumberView_xxx).toString()
//        }
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//
//        val x = width / 2f
//        val y = height / 2f
//
//        canvas.drawText(text, x, y, borderPaint)
//        canvas.drawText(text, x, y, textPaint)
//    }
//}
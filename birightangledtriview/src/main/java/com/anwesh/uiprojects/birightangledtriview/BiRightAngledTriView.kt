package com.anwesh.uiprojects.birightangledtriview

/**
 * Created by anweshmishra on 10/09/20.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.app.Activity
import android.content.Context

val colors : Array<Int> = arrayOf(
        "#F44336",
        "#00BCD4",
        "#4CAF50",
        "#009688",
        "#2196F3"
).map({Color.parseColor(it)}).toTypedArray()
val parts : Int = 5
val scGap : Float = 0.02f / parts
val strokeFactor : Int = 90
val sizeFactor : Float = 5.9f
val backColor : Int = Color.parseColor("#BDBDBD")
val delay : Long = 20
val rot : Float = 90f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawBiRightAngleTri(scale : Float, w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val sf4 : Float = sf.divideScale(3, parts)
    val size : Float = Math.min(w, h) / sizeFactor
    save()
    translate(w / 2, h / 2)
    rotate(rot * sf4)
    for (j in 0..1) {
        save()
        scale(1f - 2 * j, 1f)
        if (sf1 > 0f) {
            drawLine(0f, 0f, -size * sf1, 0f, paint)
        }
        if (sf2 > 0f) {
            drawLine(-size, 0f, -size, -size * sf2, paint)
        }
        if (sf3 > 0f) {
            drawLine(-size, -size, -size + size * sf3, -size + size * sf3, paint)
        }
        restore()
    }
    restore()
}

fun Canvas.drawBRATNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawBiRightAngleTri(scale, w, h, paint)
}

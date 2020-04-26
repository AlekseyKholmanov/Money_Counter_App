package com.example.holmi_production.money_counter_app.ui.utils

import android.animation.*
import android.content.Context
import android.content.res.ColorStateList
import android.util.Property
import android.view.View
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import com.example.holmi_production.money_counter_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
object ViewAnimation {

    fun fadeIn(v:View){
        with(v){
            alpha = 0f
            animate()
                .setDuration(200)
                .alpha(0.8f)
                .start()
        }
    }

    fun fadeOut(v:View){
        with(v){
            alpha = 0.8f
            animate()
                .setDuration(200)
                .alpha(0f)
                .start()
        }
    }


    fun testComplexANim(context: Context, v:View){
        val colorFrom = ContextCompat.getColor(context, R.color.colorAccent)
        val colorTo = ContextCompat.getColor(context, R.color.colorYellow)
        val otherDrawable = context.getDrawable(R.drawable.ic_24_sync)
//        val animator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo).apply {
//            duration = 1000
//            addUpdateListener {
//            }
//            addListener(object: Animator.AnimatorListener{
//                override fun onAnimationRepeat(animation: Animator?)= Unit
//
//                override fun onAnimationEnd(animation: Animator?) {
//                    (v as FloatingActionButton).setImageDrawable(otherDrawable)
//                    v.animate()
//                        .rotation(45f)
//                        .start()
//                }
//
//                override fun onAnimationCancel(animation: Animator?) {
//                }
//
//                override fun onAnimationStart(animation: Animator?) {
//                }
//            })
//            start()
//        }

        val a = ObjectAnimator.ofArgb(colorFrom,colorTo, colorFrom).apply {
            duration = 500
            addUpdateListener {
                (v as FloatingActionButton).backgroundTintList = ColorStateList.valueOf(it.animatedValue as Int)
            }
        }

        val b = ObjectAnimator.ofFloat(v,"rotation", 0f, 45f).apply {
            duration = 500
            addListener { object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                    (v as FloatingActionButton).setImageDrawable(otherDrawable)
                }
            } }
        }
       AnimatorSet().apply {
            playTogether(a,b)
            start()
        }

    }

    fun showIn(v: View) {
        with(v) {
            visibility = View.VISIBLE
            alpha = 0f
            translationY = v.height.toFloat()
            animate()
                .setDuration(200)
                .translationY(0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(1f)
                .start()
        }
    }

    fun showOut(v: View) {
        with(v) {
            visibility = View.VISIBLE
            alpha = 1f
            translationY = 0f
            animate()
                .setDuration(200)
                .translationY(v.height.toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        v.visibility = View.GONE
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(0f)
                .start()
        }
    }

    fun init(v:View){
        with(v){
            visibility = View.GONE
            translationY = v.height.toFloat()
            alpha = 0f
        }
    }
}
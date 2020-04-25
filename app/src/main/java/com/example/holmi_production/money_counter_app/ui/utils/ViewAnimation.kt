package com.example.holmi_production.money_counter_app.ui.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

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
package com.example.holmi_production.money_counter_app.ui.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
object ViewAnimation {

    fun rotateFab(v: View, rotate: Boolean): Boolean {
        v.animate()
            .setDuration(200)
            .setUpdateListener {
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                    }
                }
            }
            .rotation(if (rotate) 135f else 0f)
        return rotate
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
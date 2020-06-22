package com.example.holmi_production.money_counter_app.utils

import com.daimajia.swipe.SwipeLayout


open class SwipeLayoutListenerImpl : SwipeLayout.SwipeListener {

    override fun onOpen(layout: SwipeLayout?) {}

    override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {}

    override fun onStartOpen(layout: SwipeLayout?) {}

    override fun onStartClose(layout: SwipeLayout?) {}

    override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {}

    override fun onClose(layout: SwipeLayout?) {}

}
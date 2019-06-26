package com.example.holmi_production.money_counter_app.mvp

import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.keep() {
        compositeDisposable += this
        Log.d("qwerty", "start Obserfve + ${compositeDisposable.size()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        Log.d("qwerty", "destroyed all observers + ${this.hashCode()}")
    }
}
package com.example.holmi_production.money_counter_app

import android.app.Application
import com.example.holmi_production.money_counter_app.di_copy.appComponent
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initCoin()
        initFlipper()
        Stetho.initializeWithDefaults(this)
    }

    private fun initFlipper() {
        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.start()
        }
    }

    private fun initCoin(){
        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
    }

    private fun initDagger() {
//        CoreComponent.init(applicationContext)
//   //AppComponent.instance.inject(this)
    }
}
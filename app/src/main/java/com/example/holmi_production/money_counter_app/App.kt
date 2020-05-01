package com.example.holmi_production.money_counter_app

import android.app.Application
import com.example.holmi_production.money_counter_app.di.components.ApplicationComponent
import com.example.holmi_production.money_counter_app.di.components.DaggerApplicationComponent
import com.example.holmi_production.money_counter_app.di.modules.ContextModule
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader


class App : Application() {
    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDi()
        initFlipper()
    }

    private fun initFlipper() {
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)
        ) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.start()
        }
    }

    private fun initDi() {
        component = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this)).build()
    }
}
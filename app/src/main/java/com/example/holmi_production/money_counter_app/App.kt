package com.example.holmi_production.money_counter_app

import android.app.Application
import com.example.holmi_production.money_counter_app.di.appComponent
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseDriver
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseProvider
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initCoin()
        initFlipper()
    }

    private fun initFlipper() {
        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client: FlipperClient = AndroidFlipperClient.getInstance(this)
            with(client) {
                addPlugin(
                    InspectorFlipperPlugin(
                        applicationContext,
                        DescriptorMapping.withDefaults()
                    )
                )
                addPlugin(
                    DatabasesFlipperPlugin(
                        SqliteDatabaseDriver(
                            applicationContext,
                            SqliteDatabaseProvider {
                                databaseList().map { dbname ->
                                    getDatabasePath(dbname)
                                }
                            })
                    )
                )
                start()
            }
        }
    }

    private fun initCoin() {
        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
    }
}
package com.example.holmi_production.money_counter_app.di.modules

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {
    fun create(appCOntext: Context, params: WorkerParameters): ListenableWorker
}
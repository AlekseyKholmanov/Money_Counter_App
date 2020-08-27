package com.example.holmi_production.money_counter_app.model.uiModels

import android.os.Parcelable
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Parcelize
class DatePeriodType(
    val type: PeriodType = PeriodType.TODAY,
    val from: DateTime? = null,
    val to: DateTime?= null
) : Parcelable
package com.example.holmi_production.money_counter_app.model

import android.graphics.Color

enum class CategoryType(val id: Int, val isSpending: Boolean, val description: String, val color:Int) {
    SALARY(0, false, "Зарплата",Color.parseColor("#30e849")),
    FOOD(1, true, "Продукты",Color.parseColor("#30e8bd")),
    ENTERTAINMENT(2, true, "Кино, музеи...",Color.parseColor("#30c9e8")),
    BAR(3, true, "Рестораны",Color.parseColor("#3080e8")),
    HOME(4, true, "Дом",Color.parseColor("#4030e8")),
    TRANSPORT(5, true, "Транспорт",Color.parseColor("#8930e8")),
    WEAR(6, true, "Одежда",Color.parseColor("#e8308c")),
    NET(7, true, "Связь",Color.parseColor("#e83a30")),
    OTHER(8, true, "Другое",Color.parseColor("#e86830"))
}



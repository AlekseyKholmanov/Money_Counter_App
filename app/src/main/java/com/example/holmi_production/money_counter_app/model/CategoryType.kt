package com.example.holmi_production.money_counter_app.model

import android.graphics.Color

enum class CategoryType(val id: Int, val isSpending: Boolean, val description: String, val color:Int) {
    SALARY(0, false, "Зарплата",Color.parseColor("#C9F098")),
    FOOD(1, true, "Продукты",Color.parseColor("#8AD9CE")),
    ENTERTAINMENT(2, true, "Кино, музеи...",Color.parseColor("#F6C29C")),
    BAR(3, true, "Рестораны",Color.parseColor("#EB95B9")),
    HOME(4, true, "Дом",Color.parseColor("#69042E")),
    TRANSPORT(5, true, "Транспорт",Color.parseColor("#7B3604")),
    WEAR(6, true, "Одежда",Color.parseColor("#034D43")),
    NET(7, true, "Связь",Color.parseColor("#417104")),
    OTHER(8, true, "Другое",Color.parseColor("#E66D17"))
}



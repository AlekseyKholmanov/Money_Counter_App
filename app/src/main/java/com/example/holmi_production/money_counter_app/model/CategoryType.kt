package com.example.holmi_production.money_counter_app.model

enum class CategoryType(val id: Int, val isSpending: Boolean, val description: String) {
    SALARY(0, false, "Зарплата"),
    FOOD(1, true, "Продукты"),
    ENTERTAINMENT(2, true, "Кино, музеи..."),
    BAR(3, true, "Рестораны"),
    HOME(4, true, "Дом"),
    TRANSPORT(5, true, "Транспорт"),
    WEAR(6, true, "Одежда"),
    NET(7, true, "Связь"),
    OTHER(8, true, "Другое")
}



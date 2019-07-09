package com.example.holmi_production.money_counter_app.model

import android.graphics.Color

enum class CategoryType(val id: Int, val isSpending: Boolean, val description: String, val color: Int) {
    SALARY(0, false, "Зарплата", Color.parseColor("#108A52")),
    ENTERTAINMENT(1, true, "Развлечения", Color.parseColor("#F8CA01")),
    BAR(2, true, "Рестораны", Color.parseColor("#E36721")),
    FOOD(3, true, "Продукты", Color.parseColor("#C7DD19")),
    HOME(4, true, "Дом", Color.parseColor("#3ABCE7")),
    TRANSPORT(5, true, "Транспорт", Color.parseColor("#D62B29")),
    WEAR(6, true, "Одежда", Color.parseColor("#E560BB")),
    NET(7, true, "Связь", Color.parseColor("#07659E")),
    OTHER(8, true, "Другое", Color.parseColor("#E0E0E0")),
    BEATY(9, true, "Красота", Color.MAGENTA),
    SPORT(10, true, "Спорт", Color.MAGENTA),
    EDUCATION(11, true, "Образование", Color.MAGENTA),
    BOOKS(12, true, "Книги", Color.MAGENTA),
    TRAVEL(13, true, "Путешествие", Color.MAGENTA),
    FASTFOOD(14, true, "Фастфуд", Color.MAGENTA),
    FUEL(15, true, "Топливо", Color.MAGENTA),
    PETS(16, true, "Питомцы", Color.MAGENTA)

}



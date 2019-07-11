package com.example.holmi_production.money_counter_app.model

import android.graphics.Color
import com.example.holmi_production.money_counter_app.R

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
    PETS(16, true, "Питомцы", Color.MAGENTA);

    companion object {
        fun getImage(categoryType: CategoryType): Int {
            return when (categoryType) {
                SALARY -> R.drawable.ic_salary
                HOME -> R.drawable.ic_home
                ENTERTAINMENT -> R.drawable.ic_glass
                FOOD -> R.drawable.ic_food
                TRANSPORT -> R.drawable.ic_bus
                WEAR -> R.drawable.ic_clothes_2
                NET -> R.drawable.ic_network
                BAR -> R.drawable.ic_bar
                OTHER -> R.drawable.ic_other
                BEATY -> R.drawable.ic_beauty
                BOOKS -> R.drawable.ic_books
                EDUCATION -> R.drawable.ic_education
                FASTFOOD -> R.drawable.ic_fastfood
                FUEL -> R.drawable.ic_fuel
                PETS -> R.drawable.ic_pets
                SPORT -> R.drawable.ic_sport
                TRAVEL -> R.drawable.ic_travel
            }
        }
    }
}





package com.example.holmi_production.money_counter_app.model

import android.graphics.Color
import com.example.holmi_production.money_counter_app.R

enum class CategoryType(val id: Int, val spendingDirection: CategorySpendingDirection, val description: String, val color: Int) {

    SALARY(0, CategorySpendingDirection.BOTH, "Зарплата", Color.parseColor("#108A52")),
    ENTERTAINMENT(1, CategorySpendingDirection.SPENDING, "Развлечения", Color.parseColor("#FBC02D")),
    BAR(2, CategorySpendingDirection.SPENDING, "Рестораны", Color.parseColor("#FF9800")),
    FOOD(3, CategorySpendingDirection.SPENDING, "Продукты", Color.parseColor("#4CAF50")),
    FASTFOOD(14, CategorySpendingDirection.SPENDING, "Фастфуд", Color.parseColor("#80413D")),
    HOME(4, CategorySpendingDirection.SPENDING, "Дом", Color.parseColor("#3ABCE7")),
    TRANSPORT(5, CategorySpendingDirection.SPENDING, "Транспорт", Color.parseColor("#F44336")),
    WEAR(6, CategorySpendingDirection.SPENDING, "Одежда", Color.parseColor("#FF4081")),
    NET(7, CategorySpendingDirection.SPENDING, "Связь", Color.parseColor("#3F51B5")),
    OTHER(8, CategorySpendingDirection.BOTH, "Другое", Color.parseColor("#E0E0E0")),
    BEATY(9, CategorySpendingDirection.SPENDING, "Красота", Color.parseColor("#E040FB")),
    PETS(16, CategorySpendingDirection.SPENDING, "Питомцы", Color.parseColor("#FFEB3B")),
    SPORT(10, CategorySpendingDirection.SPENDING, "Спорт", Color.parseColor("#9E9E9E")),
    EDUCATION(11, CategorySpendingDirection.SPENDING, "Образование", Color.parseColor("#80413D")),
    BOOKS(12, CategorySpendingDirection.SPENDING, "Книги", Color.parseColor("#A67A77")),
    TRAVEL(13, CategorySpendingDirection.SPENDING, "Путешествие", Color.parseColor("#2196F3")),
    FUEL(15, CategorySpendingDirection.SPENDING, "Топливо", Color.parseColor("#616161"));

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
        fun getDescription(id: Int): String {
            return values()[id].description
        }
        fun getColor(typeId:Int): Int
        {
            return values()[typeId].color
        }
    }
}





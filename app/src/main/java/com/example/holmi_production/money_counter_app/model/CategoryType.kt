package com.example.holmi_production.money_counter_app.model

import android.graphics.Color
import com.example.holmi_production.money_counter_app.R

enum class CategoryType(val id: Int, val spendingDirection: CategorySpendingDirection, val description: String, val color: Int) {

    SALARY(0, CategorySpendingDirection.BOTH, "Зарплата", Color.parseColor("#108A52")),
    ENTERTAINMENT(1, CategorySpendingDirection.SPENDING, "Развлечения", Color.parseColor("#F8CA01")),
    BAR(2, CategorySpendingDirection.SPENDING, "Рестораны", Color.parseColor("#E36721")),
    FOOD(3, CategorySpendingDirection.SPENDING, "Продукты", Color.parseColor("#CAFF9E")),
    HOME(4, CategorySpendingDirection.SPENDING, "Дом", Color.parseColor("#3ABCE7")),
    TRANSPORT(5, CategorySpendingDirection.SPENDING, "Транспорт", Color.parseColor("#D62B29")),
    WEAR(6, CategorySpendingDirection.SPENDING, "Одежда", Color.parseColor("#E560BB")),
    NET(7, CategorySpendingDirection.SPENDING, "Связь", Color.parseColor("#07659E")),
    OTHER(8, CategorySpendingDirection.BOTH, "Другое", Color.parseColor("#E0E0E0")),
    BEATY(9, CategorySpendingDirection.SPENDING, "Красота", Color.parseColor("#B176EB")),
    SPORT(10, CategorySpendingDirection.SPENDING, "Спорт", Color.parseColor("#4C4546")),
    EDUCATION(11, CategorySpendingDirection.SPENDING, "Образование", Color.parseColor("#80413D")),
    BOOKS(12, CategorySpendingDirection.SPENDING, "Книги", Color.parseColor("#A67A77")),
    TRAVEL(13, CategorySpendingDirection.SPENDING, "Путешествие", Color.parseColor("#45E7FF")),
    FASTFOOD(14, CategorySpendingDirection.SPENDING, "Фастфуд", Color.parseColor("#80413D")),
    FUEL(15, CategorySpendingDirection.SPENDING, "Топливо", Color.parseColor("#EBD36F")),
    PETS(16, CategorySpendingDirection.SPENDING, "Питомцы", Color.parseColor("#66FFA8"));

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





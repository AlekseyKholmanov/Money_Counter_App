package com.example.holmi_production.money_counter_app.model

import android.graphics.Color
import com.example.holmi_production.money_counter_app.R

enum class CategoryType(val id: Int, val spendingDirection: CategorySpendingDirection, val description: String, val color: Int) {
    SALARY(0, CategorySpendingDirection.BOTH, "Зарплата", Color.parseColor("#248627")),
    ENTERTAINMENT(1, CategorySpendingDirection.SPENDING, "Развлечения", Color.parseColor("#FBC02D")),
    BAR(2, CategorySpendingDirection.SPENDING, "Рестораны", Color.parseColor("#FF9800")),
    WORK_FOOD(3, CategorySpendingDirection.SPENDING, "Обеды", Color.parseColor("#349617")),
    FOOD(4, CategorySpendingDirection.SPENDING, "Продукты", Color.parseColor("#67BB6A")),
    FASTFOOD(5, CategorySpendingDirection.SPENDING, "Фастфуд", Color.parseColor("#80413D")),
    HOME(6, CategorySpendingDirection.SPENDING, "Дом", Color.parseColor("#3ABCE7")),
    TRANSPORT(7, CategorySpendingDirection.SPENDING, "Транспорт", Color.parseColor("#F44336")),
    WEAR(8, CategorySpendingDirection.SPENDING, "Одежда", Color.parseColor("#FF4081")),
    NET(9, CategorySpendingDirection.SPENDING, "Связь", Color.parseColor("#3F51B5")),
    OTHER(10, CategorySpendingDirection.BOTH, "Другое", Color.parseColor("#E0E0E0")),
    BEATY(11, CategorySpendingDirection.SPENDING, "Красота", Color.parseColor("#E040FB")),
    PETS(12, CategorySpendingDirection.SPENDING, "Питомцы", Color.parseColor("#FFEB3B")),
    SPORT(13, CategorySpendingDirection.SPENDING, "Спорт", Color.parseColor("#9E9E9E")),
    EDUCATION(14, CategorySpendingDirection.SPENDING, "Образование", Color.parseColor("#80413D")),
    BOOKS(15, CategorySpendingDirection.SPENDING, "Книги", Color.parseColor("#A67A77")),
    TRAVEL(16, CategorySpendingDirection.SPENDING, "Путешествие", Color.parseColor("#2196F3")),
    FUEL(17, CategorySpendingDirection.SPENDING, "Топливо", Color.parseColor("#616161")),
    MEDICINE(18, CategorySpendingDirection.SPENDING, "Аптеки", Color.parseColor("#616161")),
    SAVED(19, CategorySpendingDirection.BOTH, "Сохранено", Color.parseColor("#635247"));

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
                MEDICINE -> R.drawable.ic_health
                else -> R.drawable.ic_launcher_foreground
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





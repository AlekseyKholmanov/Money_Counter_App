package com.example.holmi_production.money_counter_app.model

import android.graphics.Color
import com.example.holmi_production.money_counter_app.R

enum class CategoryType(val id: Int, val spendingDirection: SpDirection, val description: String, val color: Int) {
    ENTERTAINMENT(1, SpDirection.SPENDING, "Развлечения", Color.parseColor("#FBC02D")),
    BAR(2, SpDirection.SPENDING, "Рестораны", Color.parseColor("#FF9800")),
    WORK_FOOD(3, SpDirection.SPENDING, "Обеды", Color.parseColor("#349617")),
    FOOD(4, SpDirection.SPENDING, "Продукты", Color.parseColor("#67BB6A")),
    FASTFOOD(5, SpDirection.SPENDING, "Фастфуд", Color.parseColor("#80413D")),
    HOME(6, SpDirection.SPENDING, "Дом", Color.parseColor("#3ABCE7")),
    TRANSPORT(7, SpDirection.SPENDING, "Транспорт", Color.parseColor("#F44336")),
    WEAR(8, SpDirection.SPENDING, "Одежда", Color.parseColor("#FF4081")),
    NET(9, SpDirection.SPENDING, "Связь", Color.parseColor("#3F51B5")),
    //OTHER(10, SpDirection.BOTH, "Другое", Color.parseColor("#E0E0E0")),
    BEATY(11, SpDirection.SPENDING, "Красота", Color.parseColor("#E040FB")),
    PETS(12, SpDirection.SPENDING, "Питомцы", Color.parseColor("#FFEB3B")),
    SPORT(13, SpDirection.SPENDING, "Спорт", Color.parseColor("#9E9E9E")),
    EDUCATION(14, SpDirection.SPENDING, "Образование", Color.parseColor("#80413D")),
    BOOKS(15, SpDirection.SPENDING, "Книги", Color.parseColor("#A67A77")),
    TRAVEL(16, SpDirection.SPENDING, "Путешествие", Color.parseColor("#2196F3")),
    FUEL(17, SpDirection.SPENDING, "Топливо", Color.parseColor("#616161")),
    MEDICINE(18, SpDirection.SPENDING, "Аптеки", Color.parseColor("#616161"));
    //(19, SpDirection.BOTH, "Сохранено", Color.parseColor("#635247"));

    companion object {
        fun getImage(categoryType: CategoryType): Int {
            return when (categoryType) {
                //SALARY -> R.drawable.ic_salary
                HOME -> R.drawable.ic_home
                ENTERTAINMENT -> R.drawable.ic_glass
                FOOD -> R.drawable.ic_food
                TRANSPORT -> R.drawable.ic_bus
                WEAR -> R.drawable.ic_clothes_2
                NET -> R.drawable.ic_network
                BAR -> R.drawable.ic_bar
                //OTHER -> R.drawable.ic_other
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





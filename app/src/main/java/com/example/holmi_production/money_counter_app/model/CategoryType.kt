package com.example.holmi_production.money_counter_app.model

enum class CategoryClass(i: Int) {
    SALARY(0),
    SALE(1),
    OTHER(2),
    FOOD(3),
    BAR(4),
    HOME(5),
    TRAVEL(6),

}

data class CategoryType(val categoryClass: CategoryClass, val isSpending: Boolean) {
    companion object {
        val list: List<CategoryType> = listOf(
            CategoryType(CategoryClass.SALARY, false),
            CategoryType(CategoryClass.SALE, true),
            CategoryType(CategoryClass.OTHER, true),
            CategoryType(CategoryClass.FOOD, true),
            CategoryType(CategoryClass.BAR, true),
            CategoryType(CategoryClass.HOME, true),
            CategoryType(CategoryClass.TRAVEL, true)
        )
    }
}



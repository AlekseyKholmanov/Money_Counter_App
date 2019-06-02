package com.example.holmi_production.money_counter_app.model

enum class Expense(i: Int) {
    SALARY(0),
    SALE(1),
    OTHER(2),
    FOOD(3),
    BAR(4),
    HOME(5),
    TRAVEL(6),

}

data class CategoryType(val expense: Expense, val isSpending: Boolean) {
    companion object {
        val list: List<CategoryType> = listOf(
            CategoryType(Expense.SALE, true),
            CategoryType(Expense.OTHER, true),
            CategoryType(Expense.FOOD, true),
            CategoryType(Expense.BAR, true),
            CategoryType(Expense.HOME, true),
            CategoryType(Expense.TRAVEL, true)
        )
    }
}



package com.example.holmi_production.money_counter_app.model

enum class Expense(val id: Int,val isSpending: Boolean) {
    SALARY(0,false),
    SALE(1,true),
    OTHER(2,true),
    FOOD(3,true),
    BAR(4,true),
    HOME(5,true),
    TRAVEL(6,true)

}

//data class CategoryType(val expense: Expense, val isSpending: Boolean) {
//    companion object {
//        val list: List<CategoryType> = listOf(
//            CategoryType(Expense.SALARY,false),
//            CategoryType(Expense.SALE, true),
//            CategoryType(Expense.OTHER, true),
//            CategoryType(Expense.FOOD, true),
//            CategoryType(Expense.BAR, true),
//            CategoryType(Expense.HOME, true),
//            CategoryType(Expense.TRAVEL, true)
//        )
//    }
//}



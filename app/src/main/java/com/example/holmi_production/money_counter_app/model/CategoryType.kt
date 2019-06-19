package com.example.holmi_production.money_counter_app.model

enum class Expense(val id: Int,val isSpending: Boolean, val description:String) {
    SALARY(0,false, "зарплата"),
    FOOD(1,true,"продукты"),
    ENTERTAINMENT(2,true,"Кино, музеи..."),
    BAR(3,true, "рестораны"),
    HOME(4,true,"дом"),
    TRANSPORT(5,true, "транспорт"),
    WEAR(6,true, "Одежда"),
    NET(7,true,"Связь"),
    OTHER(8,true,"другое")



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



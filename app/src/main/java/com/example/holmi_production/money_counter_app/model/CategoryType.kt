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

data class CategoryType(val categoryClass: CategoryClass, val isSpending: Boolean){
    companion object{
        val FOOD = CategoryType(CategoryClass.FOOD,true)
        val SALARY = CategoryType(CategoryClass.SALARY, false)
        val OTHER_SPENDING=CategoryType(CategoryClass.OTHER,true)
    }
}



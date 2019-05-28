package com.example.holmi_production.money_counter_app.model


enum class CategoryClass {
    SALARY,
    SALE,
    OTHER,
    FOOD
}

data class CategoryType(val categoryClass: CategoryClass, val isSpending: Boolean){
    companion object{
        val FOOD = CategoryType(CategoryClass.FOOD,true)
        val SALARY = CategoryType(CategoryClass.SALARY, false)
        val OTHER_SPENDING=CategoryType(CategoryClass.OTHER,true)
    }
}



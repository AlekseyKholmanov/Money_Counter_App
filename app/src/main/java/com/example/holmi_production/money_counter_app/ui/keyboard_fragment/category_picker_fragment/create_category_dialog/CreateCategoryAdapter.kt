package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.create_category_dialog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.holmi_production.money_counter_app.model.entity.Category

class CreateCategoryAdapter(
    fm: FragmentManager,
    val categories: Array<Category>,
    val subcategoryCallback: ISubcategoryCreateCallback,
    val categoryCallback: ICategoryCreateCallback) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
         return when (CategoryCreateDialogType.values()[position]) {
            CategoryCreateDialogType.CATEGORY -> {
                val instance = CategoryCreateFragment.newInstance()
                instance.setCallback(categoryCallback)
                instance
            }
            CategoryCreateDialogType.SUBCATEGORY -> {
                val instance = CreateSubcategoryFragment.newInstance(categories)
                instance.setCallback(subcategoryCallback)
                instance
            }
        }
    }

    override fun getCount(): Int = CategoryCreateDialogType.values().count()

    override fun getPageTitle(position: Int): CharSequence {
        return when (CategoryCreateDialogType.values()[position]) {
            CategoryCreateDialogType.CATEGORY -> "Категория"
            CategoryCreateDialogType.SUBCATEGORY -> "Подкатегория"
        }
    }
}

enum class CategoryCreateDialogType(val number: Int) {
    CATEGORY(0),
    SUBCATEGORY(1)
}
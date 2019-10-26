package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.holmi_production.money_counter_app.ui.charts_fragments.ChartType

class CategoryFragmentManager(
    fm: FragmentManager,
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
    val subcategoryCallback: ISubcategoryCreateCallback,
    val categoryCallback: ICategoryCreateCallback) :
    FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
         return when (CategoryCreateDialogType.values()[position]) {
            CategoryCreateDialogType.CATEGORY -> {
                val instance = CategoryCreateFragment.newInstance()
                instance.setCallback(categoryCallback)
                instance
            }
            CategoryCreateDialogType.SUBCATEGORY -> {
                val instance = FragmentCreateSubcategory.newInstance()
                instance.setCallback(subcategoryCallback)
                instance
            }
        }
    }

    override fun getCount(): Int = ChartType.values().count()

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
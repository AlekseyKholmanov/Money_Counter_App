package com.example.holmi_production.money_counter_app.model.enums

import androidx.annotation.DrawableRes
import com.example.holmi_production.money_counter_app.R

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
enum class Images(@DrawableRes val imageResId: Int) {
    Image1(R.drawable.ic_cat_bar),
    Image2(R.drawable.ic_cat_beauty),
    Image3(R.drawable.ic_cat_books),
    Image4(R.drawable.ic_cat_bus),
    Image5(R.drawable.ic_cat_clothes),
    Image6(R.drawable.ic_cat_clothes_2),
    Image7(R.drawable.ic_cat_education),
    Image8(R.drawable.ic_cat_fastfood),
    Image9(R.drawable.ic_cat_food),
    Image10(R.drawable.ic_cat_fuel),
    Image11(R.drawable.ic_cat_glass),
    Image12(R.drawable.ic_cat_health),
    Image13(R.drawable.ic_cat_home),
    Image14(R.drawable.ic_cat_network),
    Image15(R.drawable.ic_cat_other),
    Image16(R.drawable.ic_cat_pets),
    Image17(R.drawable.ic_cat_salary),
    Image18(R.drawable.ic_cat_sport),
    Image19(R.drawable.ic_cat_travel);

    companion object {
        const val NO_IMAGE = -1

        fun getImageById(id: Int): Int {
            return if (id == NO_IMAGE) {
                R.drawable.img_no_image
            } else {
                values()[id].imageResId
            }
        }
    }
}
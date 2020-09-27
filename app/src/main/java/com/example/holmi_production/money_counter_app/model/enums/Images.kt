package com.example.holmi_production.money_counter_app.model.enums

import androidx.annotation.DrawableRes
import com.example.holmi_production.money_counter_app.R

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
enum class Images(@DrawableRes val imageResId: Int) {
    Image1(R.drawable.img_cat_bar),
    Image2(R.drawable.img_cat_beauty),
    Image3(R.drawable.img_cat_books),
    Image4(R.drawable.img_cat_bus),
    Image5(R.drawable.img_cat_clothes),
    Image6(R.drawable.img_cat_clothes_2),
    Image7(R.drawable.img_cat_education),
    Image8(R.drawable.img_cat_fastfood),
    Image9(R.drawable.img_cat_food),
    Image10(R.drawable.img_cat_fuel),
    Image11(R.drawable.img_cat_glass),
    Image12(R.drawable.img_cat_health),
    Image13(R.drawable.img_cat_home),
    Image14(R.drawable.img_cat_network),
    Image15(R.drawable.img_cat_other),
    Image16(R.drawable.img_cat_pets),
    Image17(R.drawable.img_cat_salary),
    Image18(R.drawable.img_cat_sport),
    Image19(R.drawable.img_cat_travel),
    Image20(R.drawable.ic_cat_art_and_design),
    Image21(R.drawable.ic_settings),
    Image22(R.drawable.ic_cat_box),
    Image23(R.drawable.ic_cat_brainstorming),
    Image27(R.drawable.ic_cat_chicken),
    Image24(R.drawable.ic_cat_crane),
    Image25(R.drawable.ic_cat_delivery_man),
    Image26(R.drawable.ic_cat_farm),
    Image28(R.drawable.ic_cat_folder),
    Image29(R.drawable.ic_cat_food_and_restaurant),
    Image30(R.drawable.ic_cat_garage),
    Image31(R.drawable.ic_cat_gift),
    Image32(R.drawable.ic_cat_gym),
    Image33(R.drawable.ic_cat_heart),
    Image34(R.drawable.ic_cat_hoodie),
    Image35(R.drawable.ic_cat_idea),
    Image36(R.drawable.ic_cat_joystick),
    Image37(R.drawable.ic_cat_bots),
    Image38(R.drawable.ic_cat_menu),
    Image39(R.drawable.ic_cat_mountain),
    Image40(R.drawable.ic_cat_note),
    Image41(R.drawable.ic_cat_pet),
    Image42(R.drawable.ic_cat_run),
    Image43(R.drawable.ic_cat_shipping),
    Image44(R.drawable.ic_cat_tent),
    Image45(R.drawable.ic_cat_trailer),
    Image46(R.drawable.ic_cat_travel),
    Image47(R.drawable.ic_cat_turbo),
    Image48(R.drawable.ic_cat_vegetable_garden),
    Image49(R.drawable.ic_cat_wash);

    companion object {
        const val NO_IMAGE = -1

        fun getImageById(id: Int?): Int {
            return if (id == NO_IMAGE || id == null) {
                R.drawable.img_no_image
            } else {
                values()[id].imageResId
            }
        }
    }
}
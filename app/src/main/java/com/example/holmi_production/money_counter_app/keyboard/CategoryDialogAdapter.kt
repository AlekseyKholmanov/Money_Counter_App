package com.example.holmi_production.money_counter_app.keyboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType

class CategoryDialogAdapter(context: Context, callback: ICategoryPickedListener) : BaseAdapter() {

    var mInflater = LayoutInflater.from(context)
    val mCallback = callback
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val type = CategoryType.values()[position]
        val v = mInflater.inflate(R.layout.dialog_category_item, parent, false)
        val text = v.findViewById<TextView>(R.id.text_category_dialog)
        val image = v.findViewById(R.id.image_category_dialog) as ImageView
        v.setBackgroundColor(type.color)
        text.text = type.description
        image.setImageResource(getImage(type))
        v.setOnClickListener { mCallback.categoryPicked(type)
        parent!!.invalidate()}
        return v
    }

    override fun getItem(position: Int): Any {
        return CategoryType.values()[position]
    }

    override fun getItemId(position: Int): Long {
        return CategoryType.values()[position].id.toLong()
    }

    override fun getCount(): Int {
        return CategoryType.values().count()
    }

    private fun getImage(categoryType: CategoryType): Int {
        return when (categoryType) {
            CategoryType.SALARY -> R.drawable.icon_salary
            CategoryType.HOME -> R.drawable.icon_home
            CategoryType.ENTERTAINMENT -> R.drawable.icon_glass
            CategoryType.FOOD -> R.drawable.icon_food
            CategoryType.TRANSPORT -> R.drawable.icon_bus
            CategoryType.WEAR -> R.drawable.icon_clothes_2
            CategoryType.NET -> R.drawable.icon_network
            CategoryType.BAR -> R.drawable.icon_bar
            CategoryType.OTHER -> R.drawable.icon_other
        }
    }
}
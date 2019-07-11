package com.example.holmi_production.money_counter_app.keyboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType

class CategoryDialogAdapter(context: Context,
                            private var types:List<CategoryType>) : BaseAdapter() {

    var mInflater = LayoutInflater.from(context)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val type = types[position]
        val v = mInflater.inflate(R.layout.fragment_category_picker_item, parent, false)
        val text = v.findViewById<TextView>(R.id.text_category_dialog)
        val image = v.findViewById(R.id.image_category_dialog) as ImageView
        v.setBackgroundColor(type.color)
        text.text = type.description
        image.setImageResource(getImage(type))
        v.setOnClickListener {
            parent!!.findNavController().navigate(R.id.action_categoryPickerFragment_to_mainFragment)
        }
        return v
    }

    override fun getItem(position: Int): Any {
        return types[position]
    }

    override fun getItemId(position: Int): Long {
        return types[position].id.toLong()
    }

    override fun getCount(): Int {
        return types.count()
    }

    private fun getImage(categoryType: CategoryType): Int {
        return when (categoryType) {
            CategoryType.SALARY -> R.drawable.ic_salary
            CategoryType.HOME -> R.drawable.ic_home
            CategoryType.ENTERTAINMENT -> R.drawable.ic_glass
            CategoryType.FOOD -> R.drawable.ic_food
            CategoryType.TRANSPORT -> R.drawable.ic_bus
            CategoryType.WEAR -> R.drawable.ic_clothes_2
            CategoryType.NET -> R.drawable.ic_network
            CategoryType.BAR -> R.drawable.ic_bar
            CategoryType.OTHER -> R.drawable.ic_other
            CategoryType.BEATY -> R.drawable.ic_beauty
            CategoryType.BOOKS -> R.drawable.ic_books
            CategoryType.EDUCATION -> R.drawable.ic_education
            CategoryType.FASTFOOD -> R.drawable.ic_fastfood
            CategoryType.FUEL -> R.drawable.ic_fuel
            CategoryType.PETS -> R.drawable.ic_pets
            CategoryType.SPORT -> R.drawable.ic_sport
            CategoryType.TRAVEL -> R.drawable.ic_travel
        }
    }
}
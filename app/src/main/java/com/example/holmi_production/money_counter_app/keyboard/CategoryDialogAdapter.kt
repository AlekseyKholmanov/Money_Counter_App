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
        val v = mInflater.inflate(R.layout.dialog_category_item, parent, false)
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
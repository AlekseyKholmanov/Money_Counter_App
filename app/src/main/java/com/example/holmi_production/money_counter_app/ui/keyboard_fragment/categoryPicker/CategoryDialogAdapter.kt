package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPicker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType

class CategoryDialogAdapter(
    context: Context,
    private var types: List<CategoryType>) : BaseAdapter() {

    private var mInflater = LayoutInflater.from(context)

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val type = types[position]
        val v = mInflater.inflate(R.layout.category_picker_item, parent, false)
        val text = v.findViewById<TextView>(R.id.text_category_dialog)
        val image = v.findViewById(R.id.image_category_dialog) as ImageView
        v.setBackgroundColor(type.color)
        text.text = type.description
        image.setImageResource(CategoryType.getImage(type))
        v.setOnClickListener {
            val bundle = bundleOf("categoryId" to type.id)
            parent!!.findNavController().navigate(R.id.action_categoryPickerFragment_to_mainFragment, bundle)
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
}
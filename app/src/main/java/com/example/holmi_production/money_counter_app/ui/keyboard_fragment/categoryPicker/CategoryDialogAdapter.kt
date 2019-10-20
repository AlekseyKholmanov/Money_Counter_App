package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType

class CategoryDialogAdapter(
    private var types: MutableList<CategoryType>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = types.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryHolder) {
            val type = types[position]
            holder.bind(type)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.category_picker_item, parent, false)
        return CategoryHolder(item, parent)
    }

    fun setCategory(categories: MutableList<CategoryType>) {
        types.clear()
        types.addAll(categories)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(var v: View, var parent: ViewGroup?) : RecyclerView.ViewHolder(v) {
        val image = v.findViewById(R.id.image_category_dialog) as ImageView
        val text = v.findViewById<TextView>(R.id.text_category_dialog)

        fun bind(type: CategoryType) {
            image.setImageResource(CategoryType.getImage(type))
            v.setBackgroundColor(type.color)
            text.text = type.description
            v.setOnClickListener {
                val bundle = bundleOf("categoryId" to type.id)
                parent!!.findNavController().navigate(R.id.action_categoryPickerFragment_to_mainFragment, bundle)
            }
        }

    }
}
interface ICategoryPicker{
    fun categoryPicked(categoryId:Int)
}
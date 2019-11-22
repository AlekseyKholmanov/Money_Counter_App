package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SquareImageView
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SubCategory

class CategoryPickerAdapter(val callback:ICategoryPickerCallback):RecyclerView.Adapter<CategoryPickerAdapter.CategoryPickerHolder>(){
    val types:ArrayList<Pair<Category,List<SubCategory>>> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryPickerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.category_picker_item, parent, false)
        return CategoryPickerHolder(item, callback)
    }

    fun setCategory(categories: ArrayList<Pair<Category, List<SubCategory>>>) {
        types.clear()
        types.addAll(categories)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = types.size

    override fun onBindViewHolder(holder: CategoryPickerHolder, position: Int) {
        holder.bind(types[position])
    }

    inner class CategoryPickerHolder(
        private val v: View,
        private val callback: ICategoryPickerCallback): RecyclerView.ViewHolder(v){
        private val tv:TextView = v.findViewById(R.id.text_category_dialog)
        private val image:SquareImageView = v.findViewById(R.id.image_category_dialog)
        private val subCategoryIndicator:ImageView = v.findViewById(R.id.image_subcategory_indicator)


        fun bind(pair: Pair<Category, List<SubCategory>>){
            val (category, subcategories) = pair
            tv.text = category.description
            v.setBackgroundColor(category.color?:Color.MAGENTA)
            v.setOnClickListener {
                callback.categoryPicked(category.id)
            }
            if(subcategories.isNotEmpty())
                subCategoryIndicator.visibility = View.VISIBLE
            else
                subCategoryIndicator.visibility = View.GONE
            image.setImageResource(category.imageId ?: R.drawable.ic_launcher_foreground)

        }
    }
}
interface ICategoryPickerCallback{
    fun categoryPicked(categoryId:Int)
}
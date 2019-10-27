package com.example.holmi_production.money_counter_app.ui.costs_fragment.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingWithCategory
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

class CostsItemDelegate : AdapterDelegate<List<ListItem>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costs_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun isForViewType(items: List<ListItem>, position: Int): Boolean {
        return items[position] is SpendingWithCategory
    }

    override fun onBindViewHolder(
        items: List<ListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        list: List<Any>) {
        if (holder is ViewHolder) {
            val item = items[position] as SpendingWithCategory
            holder.bind(item)
        }
    }

    class ViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {

        //private val date: AppCompatTextView = v.findViewById(R.createdDate.cost_item_date)
        private val mCategory: AppCompatTextView = v.findViewById(R.id.cost_item_category)
        private val image:ImageView = v.findViewById(R.id.cost_item_image)
        private val sum: AppCompatTextView = v.findViewById(R.id.cost_item_sum)
        private val sign: AppCompatTextView = v.findViewById(R.id.cost_item_sign)
        private val comment:AppCompatTextView = v.findViewById(R.id.cost_item_comment)
        private val view = v

        fun bind(spendingWithCategory: SpendingWithCategory) {
            //TODO баг при первом вызове когда категорий нет
            val spending = spendingWithCategory.spending
            val category: Category? = if(spendingWithCategory.category.isNullOrEmpty()) null else spendingWithCategory.category[0]
            val color = if (spending.isSpending) Color.parseColor("#c62828") else  Color.parseColor("#2e7d32")

            val signText = if (spending.isSpending) "-" else "+"
            view.setBackgroundColor(category?.color ?: Color.TRANSPARENT)
            view.background.alpha = 160
            image.setImageResource(category?.imageId ?: R.drawable.ic_launcher_foreground)
            comment.text = spending.comment ?: ""
            sum.text = spending.sum.toCurencyFormat().withRubleSign()
            sum.setTextColor(color)
            sign.text = signText
            sign.setTextColor(color)
            mCategory.text = category?.description
        }
    }
}
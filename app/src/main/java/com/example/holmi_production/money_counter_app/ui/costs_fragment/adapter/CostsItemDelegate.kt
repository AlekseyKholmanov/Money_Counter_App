package com.example.holmi_production.money_counter_app.ui.costs_fragment.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.getTime
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

class CostsItemDelegate : AdapterDelegate<List<ListItem>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cost, parent, false)
        return ViewHolder(view)
    }

    override fun isForViewType(items: List<ListItem>, position: Int): Boolean {
        return items[position] is SpendingListItem
    }

    override fun onBindViewHolder(
        items: List<ListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        list: List<Any>) {
        if (holder is ViewHolder) {
            val item = items[position] as SpendingListItem
            holder.bind(item)
        }
    }

    class ViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {

        private val mCategoryText: AppCompatTextView = v.findViewById(R.id.tv_cost_item_category)
        private val mImage: ImageView = v.findViewById(R.id.cost_item_image)
        private val mSum: AppCompatTextView = v.findViewById(R.id.cost_item_sum)
        private val mComment: AppCompatTextView = v.findViewById(R.id.tv_cost_item_comment)
        private val mDate: AppCompatTextView = v.findViewById(R.id.cost_item_date)
        private val shapeContainer: ConstraintLayout =
            v.findViewById(R.id.spending_item_shape_container)

        fun bind(item: SpendingListItem) {
            val spending = item.spending
            val subcategory = item.subCategory
            val category = item.category
            val signText = if (spending.isSpending == SpDirection.SPENDING) "-" else "+"
            val directionColor =
                if (spending.isSpending == SpDirection.SPENDING) Color.parseColor("#c62828") else Color.parseColor(
                    "#2e7d32"
                )
            val text =
                if (subcategory == null) category.description else "${category.description
                    } \u2799 ${subcategory.description}"
            val drawable = shapeContainer.background as GradientDrawable
            val sum = "$signText ${spending.sum.toCurencyFormat().withRubleSign()}"
            val backgroundColor = category?.color ?: Color.TRANSPARENT
            drawable.setColor(backgroundColor)
            mComment.visibility = if (spending.comment.isNullOrEmpty()) {
                View.GONE
            } else {
                mComment.text = spending.comment
                mComment.setTextColor(ColorUtils.getFontColor(backgroundColor))
                View.VISIBLE
            }

            mImage.setImageResource(category?.imageId ?: R.drawable.ic_launcher_foreground)
            mSum.text = sum
            mSum.setTextColor(directionColor)
            mCategoryText.text = text
            mCategoryText.setTextColor(ColorUtils.getFontColor(backgroundColor))
            mDate.text = spending.createdDate.getTime()
            mDate.setTextColor(ColorUtils.getFontColor(backgroundColor))
        }
    }
}
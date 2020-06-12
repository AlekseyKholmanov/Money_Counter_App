package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.getTime
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionItem
import com.example.holmi_production.money_counter_app.utils.ColorUtils


class TransactionItemHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val mCategoryText: AppCompatTextView = v.findViewById(R.id.tv_cost_item_category)
    private val mImage: ImageView = v.findViewById(R.id.cost_item_image)
    private val mSum: AppCompatTextView = v.findViewById(R.id.cost_item_sum)
    private val mComment: AppCompatTextView = v.findViewById(R.id.tv_cost_item_comment)
    private val mDate: AppCompatTextView = v.findViewById(R.id.cost_item_date)
    private val shapeContainer: ConstraintLayout =
        v.findViewById(R.id.spending_item_shape_container)

    fun bind(item: TransactionItem) {

        with(itemView) {
            val transaction = item.transaction
            val subcategory = item.subcategory
            val category = item.category
            val directionColor =
                if (transaction.sum > 0) Color.parseColor("#c62828") else Color.parseColor(
                    "#2e7d32"
                )
            val text =
                if (subcategory == null) category?.description else "${category?.description
                } \u2799 ${subcategory.description}"
            val drawable = shapeContainer.background as GradientDrawable
            val sum = "${transaction.sum.toCurencyFormat().withRubleSign()}"
            val backgroundColor = category?.color ?: Color.TRANSPARENT
            drawable.setColor(backgroundColor)
            mComment.visibility = if (transaction.comment.isNullOrEmpty()) {
                View.GONE
            } else {
                mComment.text = transaction.comment
                mComment.setTextColor(ColorUtils.getFontColor(backgroundColor))
                View.VISIBLE
            }

            mImage.setImageResource(category?.imageId ?: R.drawable.ic_launcher_foreground)
            mSum.text = sum
            mSum.setTextColor(directionColor)
            mCategoryText.text = text
            mCategoryText.setTextColor(ColorUtils.getFontColor(backgroundColor))
            mDate.text = transaction.createdDate.getTime()
            mDate.setTextColor(ColorUtils.getFontColor(backgroundColor))
        }
    }
}
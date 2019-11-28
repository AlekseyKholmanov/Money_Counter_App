package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem

class PieChartAdapter(private val spendings: Array<SpendingListItem>) :
    RecyclerView.Adapter<PieChartAdapter.PieChartHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieChartHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_cost, parent, false)
        return PieChartHolder(item)
    }

    override fun getItemCount(): Int = spendings.size

    override fun onBindViewHolder(holder: PieChartHolder, position: Int) {
        holder.bind(spendings[position])
    }

    inner class PieChartHolder(val v: View) : RecyclerView.ViewHolder(v) {
        private val mCategoryText: AppCompatTextView = v.findViewById(R.id.tv_cost_item_category)
        private val mImage: ImageView = v.findViewById(R.id.cost_item_image)
        private val mSum: AppCompatTextView = v.findViewById(R.id.cost_item_sum)
        private val mSign: AppCompatTextView = v.findViewById(R.id.cost_item_sign)
        private val mComment: AppCompatTextView = v.findViewById(R.id.tv_cost_item_comment)

        fun bind(item: SpendingListItem) {
            val spending = item.spending
            val subcategory = item.subCategory
            val category = item.category
            val signText = if (spending.isSpending == SpDirection.SPENDING) "-" else "+"
            val directionColor = if (spending.isSpending == SpDirection.SPENDING) Color.parseColor("#c62828") else Color.parseColor("#2e7d32")
            val subcategoryText = if (subcategory == null) "" else " \u2799 ${subcategory.description}"
            val categoryText = category?.description ?: ""
            mImage.visibility = View.GONE
            mSum.text = spending.sum.toCurencyFormat().withRubleSign()
            mSum.setTextColor(directionColor)
            mSign.text = signText
            mSign.setTextColor(directionColor)
            mCategoryText.text = categoryText + subcategoryText
            mComment.text = spending.comment ?: ""
        }
    }
}
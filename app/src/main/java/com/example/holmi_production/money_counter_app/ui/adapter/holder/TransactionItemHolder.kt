package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.getTime
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionItem
import com.example.holmi_production.money_counter_app.utils.SwipeLayoutListenerImpl
import kotlinx.android.synthetic.main.item_transaction.view.*


class TransactionItemHolder(
    private val callback: Callback,
    private val itemManager: SwipeItemRecyclerMangerImpl,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipe)

    private val deleteButton: ImageButton = itemView.findViewById(R.id.transactionDeleteButton)

    private val bottomWrapper: FrameLayout = itemView.findViewById(R.id.bottomWrapper)

    private val swipeListener = object : SwipeLayoutListenerImpl() {
        override fun onOpen(layout: SwipeLayout?) {
            itemManager.closeAllExcept(layout)
        }
    }

    init {
        swipeLayout.addSwipeListener(swipeListener)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, bottomWrapper)
        swipeLayout.showMode = SwipeLayout.ShowMode.PullOut
    }

    fun bind(item: TransactionItem) {

        with(itemView) {
            val transaction = item.transaction
            val subcategory = item.subcategory
            val category = item.category
            val directionColor =
                if (transaction.sum < 0) Color.parseColor("#c62828") else Color.parseColor(
                    "#2e7d32"
                )

            val text = if (category == null) {
                if (transaction.sum > 0) context.getString(R.string.process_income)
                else context.getString(R.string.process_spending)
            } else if (subcategory == null) {
                category.description
            } else {
                "${category.description} \u2799 ${subcategory.description}"
            }

            val backgroundColor = category?.color ?: Color.LTGRAY

            val sum = transaction.sum.toCurencyFormat().withRubleSign()
            //comment
            itemComment.visibility = if (transaction.comment.isNullOrEmpty()) {
                View.GONE
            } else {
                itemComment.text = transaction.comment
                View.VISIBLE
            }
            //image
            if (category == null) {
                indicator.visibility = View.GONE
                if (transaction.sum > 0) {
                    itemImage.load(R.drawable.ic_money_got)
                } else {
                    itemImage.load(R.drawable.ic_money_loss)
                }
            } else {
                itemImage.visibility = View.VISIBLE
                itemImage.load(Images.getImageById(item.category.imageId))
                indicator.visibility = View.VISIBLE
                indicator.setColors(listOf(backgroundColor))
            }

            //sum
            with(itemSum) {
                setText(sum)
                setTextColor(directionColor)
            }
            //text field
            accountId.text = item.account.description
            itemCategory.text = text
            itemDate.text = transaction.createdDate.getTime()

            deleteButton.setOnClickListener {
                itemManager.closeAllItems()
                callback.deleteTransaction(item.transaction.id)
            }
        }
    }


    interface Callback {
        fun openTransactionDetails(transactionId: String)
        fun deleteTransaction(transactionId: String)
    }
}
package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class ImageHolder(
    v: View
) : RecyclerView.ViewHolder(v) {

    fun bind(imageId: Int, position: Int) {
        with(itemView) {
            with(categoryItem) {
                setImage(imageId)
                setTextVisibility(false)
            }
        }
    }

    interface Callback {
        fun imagePicked(arrayImagePosition: Int)
    }
}
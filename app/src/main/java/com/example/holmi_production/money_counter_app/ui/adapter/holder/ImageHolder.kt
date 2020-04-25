package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SquareImageView
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class ImageHolder(
    v: View,
    private val callback: Callback
): RecyclerView.ViewHolder(v){

    fun bind(imageId:Int, position:Int){
        with(itemView){
            image_category_dialog.load(imageId)
            image_category_dialog.visibility =  View.VISIBLE
            image_category_dialog.setOnClickListener {
                callback.imagePicked(position)
            }
            text_category_dialog. visibility = View.GONE
        }
    }

    interface Callback{
        fun imagePicked(arrayImagePosition: Int)
    }
}
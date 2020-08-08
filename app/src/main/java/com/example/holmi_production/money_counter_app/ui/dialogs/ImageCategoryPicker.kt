package com.example.holmi_production.money_counter_app.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.ui.adapter.IconPickerAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.holder.ImageHolder
import kotlinx.android.synthetic.main.fragment_category_icon_picker.*

class ImageCategoryPicker(
    context: Context,
    private val OnImagePicked: (Int) -> Unit = {}) : Dialog(context)
{

    var callback = object : ImageHolder.Callback {
        override fun imagePicked(arrayImagePosition: Int) {
            OnImagePicked(arrayImagePosition)
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_category_icon_picker)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.bg_rounded_8dp))
        val ids = Images.values().map { it.imageResId }
        with(rv_images){
            adapter = IconPickerAdapter(ids, callback )
        }
    }
}


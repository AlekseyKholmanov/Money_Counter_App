package com.example.holmi_production.money_counter_app.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.holmi_production.money_counter_app.R
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
        val imageIds = context.resources.obtainTypedArray(R.array.images)
        val ids = mutableListOf<Int>()
            for(i in 0 until imageIds.length()){
            ids.add(imageIds.getResourceId(i, -1))
        }
        imageIds.recycle()
        with(rv_images){
            adapter = IconPickerAdapter(ids, callback )
        }
    }
}


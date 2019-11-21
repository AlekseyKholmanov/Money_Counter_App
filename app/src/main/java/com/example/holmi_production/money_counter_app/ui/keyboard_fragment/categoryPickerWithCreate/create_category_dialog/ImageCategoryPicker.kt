package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppComaptDialogFragment
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_image_picker.*

class ImageCategoryPicker :AndroidXMvpAppComaptDialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageIds = context!!.resources.obtainTypedArray(R.array.images)
        val adapter = ImagePickerAdapter(imageIds, context!!)
        val layoutManager = GridLayoutManager(context, 3)
        rv_images.layoutManager = layoutManager
        rv_images.adapter = adapter
    }


}

interface ICategoryPicker{
    fun categoryPicked(resId:Int)
}
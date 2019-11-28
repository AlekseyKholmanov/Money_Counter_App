package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppComaptDialogFragment

class ImageCategoryPicker : AndroidXMvpAppComaptDialogFragment(){

    lateinit var rv: RecyclerView
    var callback: IImagePicker? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.fragment_image_picker,null)
        val imageIds = context!!.resources.obtainTypedArray(R.array.images)
        val layoutManager = GridLayoutManager(context,4)
        val adapter = ImagePickerAdapter(imageIds, callback)
        rv = view.findViewById(R.id.rv_images)
        rv.layoutManager = layoutManager
        rv.adapter = adapter
        builder.setView(view)
        adapter.notifyDataSetChanged()
        return builder.create()
    }

    fun setListener(callback:IImagePicker){
        this.callback = callback
    }

    companion object{
        fun newInstance(): ImageCategoryPicker {
            return ImageCategoryPicker()
        }
    }
}

interface IImagePicker{
    fun imagePicked(resId:Int)
}
package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.content.Context
import android.database.DataSetObserver
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.dialog_create_subcategory.*
import kotlinx.android.synthetic.main.fragment_create_subcategory.*
import leakcanary.AppWatcher

class FragmentCreateSubcategory : AndroidXMvpAppCompatFragment() {

    companion object {
        fun newInstance(categories: Array<Category>): FragmentCreateSubcategory {
            val bundle = Bundle()
            bundle.putParcelableArray("categories", categories)
            val fragment = FragmentCreateSubcategory()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var callback: ISubcategoryCreateCallback? = null

    fun setCallback(callback: ISubcategoryCreateCallback) {
        this.callback = callback
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_FragmentCreateSub", "stopped")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_FragmentSubcategory", "Paused")
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_create_subcategory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categories = arguments!!.getParcelableArray("categories") as Array<Category>
        val adapter = SpinnerSubcategoryAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, categories)
        spinner_parentCategory.adapter = adapter
        spinner_parentCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val category = parent!!.selectedItem as Category
                Toast.makeText(
                    context,
                    "categoryId:${category.description}, usage count: ${category.usageCount}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        btn_create_subcategory.setOnClickListener {
            val pickedCategory = spinner_parentCategory.selectedItem as Category
            callback!!.subcategoryCreated(et_subcategory_name.text.toString(),pickedCategory.id)
        }
    }

}

interface ISubcategoryCreateCallback {
    fun subcategoryCreated(categoryName:String, parentId:Int)
}
package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.ui.adapter.SpinnerSubcategoryAdapter
import kotlinx.android.synthetic.main.part_create_subcategory.*

class CreateSubcategoryFragment : AndroidXMvpAppCompatFragment() {

    companion object {
        fun newInstance(categories: Array<CategoryEntity>): CreateSubcategoryFragment {
            val bundle = Bundle()
            bundle.putParcelableArray("categories", categories)
            val fragment =
                CreateSubcategoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.part_create_subcategory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categories = requireArguments().getParcelableArray("categories") as Array<CategoryEntity>
        val adapter =
            SpinnerSubcategoryAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                categories
            )
        spinner_parentCategory.adapter = adapter
        spinner_parentCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val category = parent!!.selectedItem as CategoryEntity
                    Toast.makeText(
                        context,
                        "categoryId:${category.description}, usage count: ${category.usageCount}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        btn_create_subcategory.setOnClickListener {
            val pickedCategory = spinner_parentCategory.selectedItem as CategoryEntity
            callback!!.subcategoryCreated(et_subcategory_name.text.toString(), pickedCategory.id, colot = pickedCategory.color)
        }
        et_subcategory_name.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                et_subcategory_name?.hideKeyboardFrom(requireContext())
                handled = true
            }
            return@OnEditorActionListener handled
        })
        et_subcategory_name.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                et_subcategory_name?.hideKeyboardFrom(requireContext())
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
}

interface ISubcategoryCreateCallback {
    fun subcategoryCreated(categoryName: String, parentId: Int, colot: Int)
}
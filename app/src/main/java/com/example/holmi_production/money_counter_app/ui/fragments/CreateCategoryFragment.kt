package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.part_create_category.*
import leakcanary.AppWatcher

class CategoryCreateFragment : AndroidXMvpAppCompatFragment(),
    ICategoryStateListener {
    override fun updateStateButton(isEnable: Boolean) {
        btn_create_category.isEnabled = isEnable
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.part_create_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryDetail = CategoryDetailFragment.newInstance()
        categoryDetail.setListener(this)
        childFragmentManager.beginTransaction().apply {
            add(R.id.container_detail_category, categoryDetail)
            commit()
        }
        btn_create_category.setOnClickListener {
           val category =  categoryDetail.getCurrentState()
            callback!!.categoryUpdated(category)
        }
    }

    fun setCallback(callback: ICategoryCreateCallback) {
        this.callback = callback
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_FragmentCategory", "Paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_FragmentCategory", "stopped")
    }

    private var callback: ICategoryCreateCallback? = null

    companion object {
        val IMAGE_DIALOG_TAG = "IMAGE_DIALOG_TAG"
        fun newInstance(): CategoryCreateFragment {
            return CategoryCreateFragment()
        }
    }
}

interface ICategoryCreateCallback {
    fun categoryUpdated(category:CategoryEntity)
}
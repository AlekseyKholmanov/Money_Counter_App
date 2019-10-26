package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SpDirection
import leakcanary.AppWatcher

class FragmentCreateSubcategory : Fragment(){

    companion object{
        fun newInstance(): FragmentCreateSubcategory {
            return FragmentCreateSubcategory()
        }
    }

    private var callback: ISubcategoryCreateCallback? = null

    fun setCallback(callback:ISubcategoryCreateCallback){
        this.callback = callback
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_FragmentCreateSub","stopped")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_FragmentSubcategory","Paused")
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_subcategory,container,false)
    }
}

interface ISubcategoryCreateCallback {
    fun subcategoryCreated()
}
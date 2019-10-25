package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.holmi_production.money_counter_app.R

class FragmentCreateSubcategory : Fragment(){

    companion object{
        fun newInstance(): FragmentCreateSubcategory {
            return FragmentCreateSubcategory()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_subcategory,container,false)
    }
}
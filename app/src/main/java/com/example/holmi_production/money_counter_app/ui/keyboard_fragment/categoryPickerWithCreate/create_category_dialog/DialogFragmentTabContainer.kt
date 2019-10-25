package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.base_category_create_dialog.*

class DialogFragmentTabContainer : DialogFragment() {
    companion object {
        fun newInstance(): DialogFragmentTabContainer {
            return DialogFragmentTabContainer()
        }
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder = AlertDialog.Builder(context!!)
//        val view = activity!!.layoutInflater.inflate(R.layout.base_category_create_dialog, null)
//        builder.setView(view)
//        val tabs = view.findViewById<TabLayout>(R.id.category_create_tabs)
//        val viePager = view.findViewById<ViewPager>(R.id.category_create_viewPager)
//
//        tabs.setupWithViewPager(viePager)
//        viePager.adapter = CategoryFragmentManager(childFragmentManager)
//        return builder.create()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_category_create_dialog, container, false)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category_create_tabs.setupWithViewPager(category_create_viewPager)
        category_create_viewPager.adapter =
            CategoryFragmentManager(
                childFragmentManager
            )
    }
    lateinit var pager:ViewPager
}
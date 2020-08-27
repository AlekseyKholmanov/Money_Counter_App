package com.example.holmi_production.money_counter_app.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment


class SettingsFragment : BaseFragment(R.layout.frament_settings) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        btn_clear_spending.setOnClickListener {
//            presenter.deleteData()
//        }
//        btn_clear_spending.setOnClickListener {
//            presenter.deleteCategory()
//        }
//        presenter.getEndMonth()
//        et_end_month_value.setOnClickListener {
//            val b = AlertDialog.Builder(requireContext())
//            b.setTitle("DATE of END PERIOD")
//            val array = Array(28) { "$it" }
//            b.setItems(array) { dialog, which ->
//                presenter.saveEndMonth(array[which].toInt())
//                dialog!!.dismiss()
//            }
//            b.show()
//        }
    }

//
//    override fun showMessage(message: String) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//    }
//
//    override fun showMessage(resId: Int) {
//        val message = context?.resources?.getText(resId)
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//    }
//
//    override fun updateEndMonth(day: Int) {
//        et_end_month_value.setText(day.toString())
//    }


    private class SpinnerAdapter(context: Context, resource: Int, val datas: Array<Int>) :
        ArrayAdapter<Int>(context, resource, datas) {
        override fun getCount(): Int = datas.size

        override fun getItem(position: Int): Int? = datas[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val label = super.getView(position, convertView, parent) as TextView
            label.setTextColor(Color.BLACK)
            label.text = datas[position].toString()
            return label
        }


    }
}
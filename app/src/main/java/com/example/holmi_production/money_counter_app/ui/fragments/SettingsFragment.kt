package com.example.holmi_production.money_counter_app.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.ui.presenters.SettingsPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.SettingsView
import kotlinx.android.synthetic.main.frament_settings.*
import moxy.presenter.InjectPresenter
import javax.inject.Inject


class SettingsFragment : BaseFragment(R.layout.frament_settings),
    SettingsView {

    override fun inject() {
        AppComponent.instance.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_clear_spending.setOnClickListener {
            presenter.deleteData()
        }
        btn_clear_spending.setOnClickListener {
            presenter.deleteCategory()
        }
        presenter.getEndMonth()
        et_end_month_value.setOnClickListener {
            val b = AlertDialog.Builder(requireContext())
            b.setTitle("DATE of END PERIOD")
            val array = Array(28) { "$it" }
            b.setItems(array) { dialog, which ->
                presenter.saveEndMonth(array[which].toInt())
                dialog!!.dismiss()
            }
            b.show()
        }
    }


    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resId: Int) {
        val message = context?.resources?.getText(resId)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateEndMonth(day: Int) {
        et_end_month_value.setText(day.toString())
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter


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
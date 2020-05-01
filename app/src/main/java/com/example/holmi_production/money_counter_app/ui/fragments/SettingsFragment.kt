package com.example.holmi_production.money_counter_app.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.ui.presenters.SettingsPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.SettingsView
import kotlinx.android.synthetic.main.frament_settings.*
import javax.inject.Inject


class SettingsFragment : AndroidXMvpAppCompatFragment(),
    SettingsView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frament_settings, container, false)
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
        et_end_month_value.setOnClickListener{
            val b = AlertDialog.Builder(context!!)
            b.setTitle("DATE of END PERIOD")
            val datas = arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28")
            b.setItems(datas) { dialog, which ->
                Log.d("M_SettingsFragment","id: $which ${datas[which]}")
                presenter.saveEndMonth(datas[which].toInt())
                dialog!!.dismiss()
            }
            b.show()
        }
    }


    override fun showMessage(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resId: Int) {
        val message = context?.resources?.getText(resId)
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    override fun updateEndMonth(day: Int) {
        et_end_month_value.setText(day.toString())
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter? {
        return presenter
    }

    private class SpinnerAdapter(context:Context, resource:Int, val datas:Array<Int>): ArrayAdapter<Int>(context,resource,datas){
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
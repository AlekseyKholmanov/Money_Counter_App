package com.example.holmi_production.money_counter_app.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.date_dialog.*
import org.joda.time.DateTime

class DateAlertDialog : DialogFragment(){
    companion object{
        fun newInstance(): DateAlertDialog {
            return DateAlertDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = View.inflate(context, R.layout.date_dialog,null)
        val container = root.findViewById<ConstraintLayout>(R.id.date_dialog)
        val yesterday = container.getViewById(R.id.yesterday_date)
        val today = container.getViewById(R.id.today_date)
        val select = container.getViewById(R.id.select_date)
        val time= DateTime()
        yesterday.findViewById<TextView>(R.id.mainText).text = "Вчера"
        yesterday.findViewById<TextView>(R.id.furtherText).text = time.minusDays(1).toString("dd-MM")
        today.findViewById<TextView>(R.id.mainText).text = "Сегодня"
        today.findViewById<TextView>(R.id.furtherText).text = time.toString("dd-MM")
        select.findViewById<TextView>(R.id.mainText).text = "Выбрать"
        select.findViewById<TextView>(R.id.furtherText).visibility = View.INVISIBLE

        return AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_launcher_background)
            .setView(root)
            .create()
    }
}
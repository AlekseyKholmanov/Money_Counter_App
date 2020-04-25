package com.example.holmi_production.money_counter_app.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppComaptDialogFragment
import com.example.holmi_production.money_counter_app.ui.adapter.PieAdapter

class PieDialogFragment private constructor(): AndroidXMvpAppComaptDialogFragment() {
    lateinit var rv:RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_pie_spendings, null)
        val items = arguments?.getParcelableArray("SPENDINGS") as Array<SpendingListItem>
        rv = view.findViewById(R.id.rv_spendings)
        val adapter  = PieAdapter()
            .apply {
            this.items = items.toList()
        }
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        builder.setView(view)
        return builder.create()
    }

    companion object{
        fun newInstance(bundle:Bundle): PieDialogFragment {
            val f =
                PieDialogFragment()
            f.arguments = bundle
            return f
        }
    }
}
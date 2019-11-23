package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppComaptDialogFragment
import kotlinx.android.synthetic.main.pie_spendings_dialog.*

class PieDialogFragment private constructor(): AndroidXMvpAppComaptDialogFragment() {
    lateinit var rv:RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        val view = activity!!.layoutInflater.inflate(R.layout.pie_spendings_dialog, null)
        val items = arguments?.getParcelableArray("SPENDINGS") as Array<SpendingListItem>
        rv = view.findViewById(R.id.rv_spendings)
        rv.adapter = PieChartAdapter(items)
        rv.layoutManager = LinearLayoutManager(context)
        builder.setView(view)
        return builder.create()
    }

    companion object{
        fun newInstance(bundle:Bundle): PieDialogFragment {
            val f = PieDialogFragment()
            f.arguments = bundle
            return f
        }
    }
}
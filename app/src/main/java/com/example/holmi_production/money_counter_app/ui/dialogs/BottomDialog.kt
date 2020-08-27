package com.example.holmi_production.money_counter_app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dialogHeaderItemDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dialogValueItemAdapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.bottom_dialog.*

class BottomDialog : BottomSheetDialogFragment() {

    val args: BottomDialogArgs by navArgs()

    companion object {
        val REQUEST_FROM_TRANSACTION_FRAGMENT = "request_from_transaction_fragment"
        val TOOLBAR_DATE_SELECT = "toolbar_date_select"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListDelegationAdapter(
            dialogHeaderItemDelegate(),
            dialogValueItemAdapterDelegate() {
                finish(value = it)
                dismiss()
            }
        )
        adapter.items = prepareItems()
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
    }

    private fun prepareItems(): List<RecyclerItem> {
        val items = mutableListOf<RecyclerItem>()
        when (args.dialogType) {
            TOOLBAR_DATE_SELECT -> {
                PeriodType.values().forEach {
                    items.add(DialogValueItem(it.ordinal, it.description, it.ordinal == args.selectedId))
                }
            }
        }
        return items
    }

    private fun finish( value: Int) {
        when (args.dialogType){
            TOOLBAR_DATE_SELECT -> {
                setFragmentResult(REQUEST_FROM_TRANSACTION_FRAGMENT, bundleOf(
                    "periodType" to value
                ))
            }
        }
    }

}
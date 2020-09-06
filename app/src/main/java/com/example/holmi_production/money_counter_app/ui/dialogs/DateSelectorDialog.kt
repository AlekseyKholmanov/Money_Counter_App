package com.example.holmi_production.money_counter_app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dialogHeaderItemDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dialogValueItemAdapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dialogValueTextItemAdapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueTextItem
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.bottom_dialog.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DateSelectorDialog(
    val listener: (type: Int) -> Unit
) : BottomSheetDialogFragment() {

    val args: BottomDialogArgs by navArgs()

    val getAccountUseCase: GetAccountsUseCase by inject()

    companion object {
        val REQUEST_FROM_ACCOUNT_CURRENCY_TYPE = "request_from_account_currency_type"
        val TYPE_TOOLBAR_PERIOD = "toolbar_date_select"
        val TYPE_ACCOUNT_CURRENCY = "currency_type"
        val TYPE_ACCOUNT_SELECTION = "account_selection"

        val ARGS_SELECTED_ACCOUNT_ID = "selected_account_id"
        val ARGS_SELECTED_ID = "selectedId"
        val ARGS_DIALOG_TYPE = "dialogType"
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
            },
            dialogValueTextItemAdapterDelegate {
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
            TYPE_TOOLBAR_PERIOD -> {
                PeriodType.values().forEach {
                    items.add(
                        DialogValueItem(
                            it.ordinal,
                            it.description,
                            it.ordinal == args.selectedIdInt
                        )
                    )
                }
            }
            TYPE_ACCOUNT_CURRENCY -> {
                CurrencyType.values().forEach {
                    items.add(
                        DialogValueTextItem(
                            it.ordinal,
                            "${it.name}: ${it.icon}",
                            it.ordinal == args.selectedIdInt
                        )
                    )
                }
            }
            TYPE_ACCOUNT_SELECTION ->{
                viewLifecycleOwner.lifecycleScope.launch {
                    val accounts = getAccountUseCase.getAccounts()
                    accounts.forEachIndexed { index, accountEntity ->
                        items.add(
                            DialogValueTextItem(
                                index,
                                "${accountEntity.description}",
                                accountEntity.id == ARGS_SELECTED_ID
                            )
                        )
                    }
                }
            }
        }
        return items
    }

    private fun finish(value: Int) {
        when (args.dialogType) {
            TYPE_TOOLBAR_PERIOD -> {
                listener(value)
            }
            TYPE_ACCOUNT_CURRENCY -> {
                setFragmentResult(
                    REQUEST_FROM_ACCOUNT_CURRENCY_TYPE, bundleOf(
                        TYPE_ACCOUNT_CURRENCY to value
                    )
                )
            }
        }
    }

}
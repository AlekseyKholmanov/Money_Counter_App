package com.example.holmi_production.money_counter_app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.model.enums.ShortButtonType
import com.example.holmi_production.money_counter_app.ui.viewModels.SimpleBottomKeyboardViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_keyboard_full.key0
import kotlinx.android.synthetic.main.bottom_keyboard_full.key1
import kotlinx.android.synthetic.main.bottom_keyboard_full.key2
import kotlinx.android.synthetic.main.bottom_keyboard_full.key3
import kotlinx.android.synthetic.main.bottom_keyboard_full.key4
import kotlinx.android.synthetic.main.bottom_keyboard_full.key5
import kotlinx.android.synthetic.main.bottom_keyboard_full.key6
import kotlinx.android.synthetic.main.bottom_keyboard_full.key7
import kotlinx.android.synthetic.main.bottom_keyboard_full.key9
import kotlinx.android.synthetic.main.bottom_keyboard_full.keyDelete
import kotlinx.android.synthetic.main.bottom_keyboard_full.keyDivider
import kotlinx.android.synthetic.main.bottom_keyboard_full.key_8
import kotlinx.android.synthetic.main.bottom_keyboard_full.summary
import kotlinx.android.synthetic.main.bottom_keyboard_simple.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class SimpleBottomKeyboard : BottomSheetDialogFragment() {


    private var displayedSum = "0"

    private val bottomViewModel: SimpleBottomKeyboardViewModel by viewModel()

    private val args: SimpleBottomKeyboardArgs by navArgs()

    lateinit var sign:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sign = if(args.isSubstraction) "-" else "+"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_keyboard_simple, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        key0.setOnClickListener { pressed(ShortButtonType.ZERO, "0") }
        key1.setOnClickListener { pressed(ShortButtonType.NUMERIC, "1") }
        key2.setOnClickListener { pressed(ShortButtonType.NUMERIC, "2") }
        key3.setOnClickListener { pressed(ShortButtonType.NUMERIC, "3") }
        key4.setOnClickListener { pressed(ShortButtonType.NUMERIC, "4") }
        key5.setOnClickListener { pressed(ShortButtonType.NUMERIC, "5") }
        key6.setOnClickListener { pressed(ShortButtonType.NUMERIC, "6") }
        key7.setOnClickListener { pressed(ShortButtonType.NUMERIC, "7") }
        key_8.setOnClickListener { pressed(ShortButtonType.NUMERIC, "8") }
        key9.setOnClickListener { pressed(ShortButtonType.NUMERIC, "9") }
        keyDivider.setOnClickListener { pressed(ShortButtonType.DIVIDER, ".") }
        keyDelete.setOnClickListener { pressed(ShortButtonType.DELETE) }
        enter.setOnClickListener { pressed(ShortButtonType.ENTER) }
        comment.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                comment?.hideKeyboardFrom(requireContext())
                handled = true
            }
            return@OnEditorActionListener handled
        })
        comment.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                comment?.hideKeyboardFrom(requireContext())
            }
        }
        summary.text = "$sign $displayedSum"

    }

    private fun pressed(type: ShortButtonType, value: String? = null) {
        when (type) {
            ShortButtonType.DELETE -> {
                if (displayedSum == "0") return
                else {
                    displayedSum = displayedSum.dropLast(1)
                    if (displayedSum.takeLast(1) == ".") {
                        displayedSum = displayedSum.dropLast(1)
                    }
                    if (displayedSum.isEmpty())
                        displayedSum = "0"
                }
            }
            ShortButtonType.DIVIDER -> {
                when {
                    value == "." && displayedSum.contains(".") -> return
                    displayedSum == "" -> displayedSum = "0."
                    else -> displayedSum += value
                }
            }
            ShortButtonType.ZERO -> {
                if (displayedSum == "0") return
                if (displayedSum.contains(Regex("[.].*"))) return
                else displayedSum += value

            }
            ShortButtonType.ENTER -> {
                enterPressed()
            }
            ShortButtonType.NUMERIC -> {
                if (displayedSum == "0")
                    displayedSum = ""
                if (displayedSum.contains('.') && displayedSum.takeLast(1) != ".")
                    displayedSum = displayedSum.dropLast(1)
                displayedSum += value
            }
        }
        summary.text = "$sign $displayedSum"
    }


    private fun enterPressed() {
        when (displayedSum) {
            "0" -> return
            else -> {
                val text = comment.text.toString()
                val sum = if (args.isSubstraction) {
                    -1 * displayedSum.toDouble()
                } else {
                    displayedSum.toDouble()
                }
                bottomViewModel.saveTransaction(args.accountId, sum, text)
                requireDialog().dismiss()
            }
        }
    }

}
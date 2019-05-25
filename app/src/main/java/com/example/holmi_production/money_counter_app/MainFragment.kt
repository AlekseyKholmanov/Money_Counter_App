package com.example.holmi_production.money_counter_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : AndroidXMvpAppCompatFragment(), IKeyboardListener {
    override fun zeroPressed(number: String) {
        if (text == "")
            return
        else {
            text += number
            displaySum()
        }
    }

    override fun dividerPressed(divider: String) {
        if (divider == "." && text.contains("."))
            return
        else{
            text+=divider
            displaySum()
        }
    }

    override fun enterPressed() {
        Snackbar.make(main_fragment, text, Snackbar.LENGTH_SHORT).show()
        text = ""
        displaySum()
    }

    override fun deletePressed() {
        text = text.dropLast(1)
        if (text.takeLast(1) == ".")
            text = text.dropLast(1)
        displaySum()
    }

    override fun numberPressed(simbol: String) {
        if(text.contains('.')&&text.takeLast(1)!=".")
            text = text.dropLast(1)
        text += simbol
        displaySum()
    }

    private fun displaySum() {
        if (text.contains('.')) {
            val float = text.toFloat()
            val str = String.format("%.1f", float)
            text = str
            expense.text = text
        } else
            expense.text = text
    }

    private var text: String = ""
    private lateinit var key:Keyboard

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        key = view.findViewById(R.id.keyboard)
        key.setListener(this)
    }
}

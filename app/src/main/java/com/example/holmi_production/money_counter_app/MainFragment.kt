package com.example.holmi_production.money_counter_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : AndroidXMvpAppCompatFragment(), IKeyboardListener {

    override fun enterPressed() {
        Snackbar.make(main_fragment, text, Snackbar.LENGTH_SHORT).show()
        text = "0"
        displaySum()
    }

    override fun deletePressed() {
        text = text.dropLast(1)
        if (text.takeLast(1) == ".")
            text = text.dropLast(1)
        displaySum()
    }

    override fun numberPressed(simbol: String) {
        if(simbol=="."&&text.contains("."))
            return
        val pattern ="\\w.\\d+".toRegex()
        val found = pattern.findAll(text)
        found.forEach { v->

            Log.d("qwerty1",v.value)
        }
        text += simbol
        displaySum()
    }

    private fun displaySum() {
        if (text.contains('.')) {
            val float = text.toFloat()
            val str = String.format("%.1f", float)
            expense.text = str
        } else
            expense.text = text
    }

    private var text: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val key = view.findViewById<Keyboard>(R.id.keyboard)
        key.setListener(this)
    }
}

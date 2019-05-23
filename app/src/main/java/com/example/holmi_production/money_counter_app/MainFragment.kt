package com.example.holmi_production.money_counter_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : AndroidXMvpAppCompatFragment(), IKeyboardListener {
    override fun numberPressed(number: Int) {
        expense.text = number.toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Keyboard(context!!).setListener(this as IKeyboardListener)
    }
}

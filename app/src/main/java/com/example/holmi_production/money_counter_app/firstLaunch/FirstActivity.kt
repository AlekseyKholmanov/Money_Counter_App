package com.example.holmi_production.money_counter_app.firstLaunch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatActivity
import kotlinx.android.synthetic.main.first_launch_activity.*

class FirstActivity : AndroidXMvpAppCompatActivity(), FirstLaunchView, TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        finish_activity.isEnabled = (date.text.isNotEmpty() && start_sum.text.isNotEmpty())

    }

    override fun displayDateActivity() {

    }

    @InjectPresenter
    lateinit var presenter: FirstLaunchPresenter

    @ProvidePresenter
    fun initPresenter(): FirstLaunchPresenter {
        return App.component.getFirstLaunchPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_launch_activity)
        finish_activity.setOnClickListener {
            finish()
        }
        start_sum.addTextChangedListener(this)
        date.addTextChangedListener(this)
    }

}
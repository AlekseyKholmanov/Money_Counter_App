package com.example.holmi_production.money_counter_app.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.frament_settings.*
import leakcanary.AppWatcher

class SettingsFragment : AndroidXMvpAppCompatFragment(), SettingsView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frament_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings_btn_clear_data.setOnClickListener {
            presenter.deleteData()
        }
    }


    override fun showMessage(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resId: Int) {
        val message = context?.resources?.getText(resId)
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    @ProvidePresenter
    fun providePresenter() = App.component.getSettingsPresenter()

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

}
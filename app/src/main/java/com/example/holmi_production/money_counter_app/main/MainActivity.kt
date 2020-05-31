package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_menu_currency_converting.*
import kotlinx.android.synthetic.main.include_menu_end_period_date.*
import kotlinx.android.synthetic.main.menu_drawer_custom.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {


    private val settingRepository: SettingRepository by inject()

//    private val workerInteractor: WorkerInteractor by inject()

    lateinit var appBarConfiguration: AppBarConfiguration

    val navController by lazy {
        val host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        host.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        AppComponent.instance.inject(this)
        initializeNavigation()
        val options = NavOptions.Builder().setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out)
            .setLaunchSingleTop(true)
            .build()

//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            controller.navigate(destination.id, options)
//        }
    }
//        initializeSettings()
//        initView()


    private fun initView() {
        et_end_month_value.text = settingRepository.getEndMonth().toString()

        //settings listener
        end_month_container.setOnClickListener {
            val b = AlertDialog.Builder(this)
            b.setTitle("День окончания месяца")
            val datas = resources.getStringArray(R.array.numbers)
            b.setItems(datas) { dialog, which ->
                Log.d("M_SettingsFragment", "id: $which ${datas[which]}")
//                workerInteractor.startEndMonthWorker(datas[which].toInt())
                et_end_month_value.text = datas[which]
                dialog!!.dismiss()
            }
            b.show()
        }
        balancer.setOnClickListener {
//            workerInteractor.balancePopulateWork()
        }
    }

    private fun setConverterState(state: Boolean) {

        if (state) {
            converter_value.visibility = View.VISIBLE
        } else {
            converter_value.visibility = View.GONE
        }
    }

    private fun initializeNavigation() {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.keyboardFragment,
                R.id.costsFragment,
                R.id.limitsFragment,
                R.id.chartFragment
            ), drawer
        )
        NavigationUI.setupWithNavController(navViewDrawer,navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.navHostFragment))
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initializeSettings() {

        val converterState = settingRepository.isConverterEnabled
        val defValue = settingRepository.getConverterValue()
        converter_value.text = Editable.Factory.getInstance().newEditable(defValue)
        converter_checkbox.isChecked = converterState
        setConverterState(converterState)
        converter_checkbox.setOnCheckedChangeListener { _, isChecked ->
            setConverterState(isChecked)
            settingRepository.isConverterEnabled = isChecked
        }
        converter_value.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                settingRepository.setConverterValue(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


}

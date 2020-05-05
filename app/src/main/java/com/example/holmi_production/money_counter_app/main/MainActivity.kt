package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.worker.WorkerInteractor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.include_menu_currency_converting.*
import kotlinx.android.synthetic.main.include_menu_end_period_date.*
import kotlinx.android.synthetic.main.menu_drawer_custom.*
import moxy.MvpAppCompatActivity
import org.koin.android.ext.android.inject

class MainActivity : MvpAppCompatActivity() {


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
        initializeSettings()
//        initView()

    }

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
        navViewBottom.setupWithNavController(navController)
        navViewDrawer.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.selectCategoryFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
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

        val converterState = settingRepository.isConverterEnable()
        val defValue = settingRepository.getConverterValue()
        converter_value.text = Editable.Factory.getInstance().newEditable(defValue)
        converter_checkbox.isChecked = converterState
        setConverterState(converterState)
        converter_checkbox.setOnCheckedChangeListener { _, isChecked ->
            setConverterState(isChecked)
            settingRepository.setConverter(isChecked)
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

    private fun showBottomNav() {
        navViewBottom.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        navViewBottom.visibility = View.GONE

    }


}

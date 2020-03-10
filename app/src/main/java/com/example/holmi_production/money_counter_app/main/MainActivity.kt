package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideDelayed
import com.example.holmi_production.money_counter_app.extensions.showDelayed
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatActivity
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.ui.charts_fragments.ChartFragment
import com.example.holmi_production.money_counter_app.ui.costs_fragment.CostsFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.KeyboardFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.CategoryPickerFragment
import com.example.holmi_production.money_counter_app.ui.limits_fragment.LimitsFragment
import com.example.holmi_production.money_counter_app.ui.topbar_fragment.TopbarFragment
import com.example.holmi_production.money_counter_app.worker.WorkerInteractor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.menu_currency_converting.*
import kotlinx.android.synthetic.main.menu_drawer_custom.*
import kotlinx.android.synthetic.main.menu_end_period_date.*
import leakcanary.AppWatcher
import javax.inject.Inject

class MainActivity : AndroidXMvpAppCompatActivity() {

    @Inject
    lateinit var settingRepository: SettingRepository

    @Inject
    lateinit var workerInteractor: WorkerInteractor

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
        Log.d("M_MainActivity", "setting: $settingRepository")
        initializeDrawers()
        initializeFragments()
        initializeSettings()
        setBottomNavigationController()
        workerInteractor.cancelAll()
        workerInteractor.startBalanceWorker()
        workerInteractor.startNotificationWorker()

        et_end_month_value.text = settingRepository.getEndMonth().toString()

        //settings listener
        end_month_container.setOnClickListener {
            val b = AlertDialog.Builder(this)
            b.setTitle("День окончания месяца")
            val datas = resources.getStringArray(R.array.numbers)
            b.setItems(datas) { dialog, which ->
                Log.d("M_SettingsFragment", "id: $which ${datas[which]}")
                workerInteractor.startEndMonthWorker(datas[which].toInt())
                et_end_month_value.text = datas[which]
                dialog!!.dismiss()
            }
            b.show()
        }
        balancer.setOnClickListener {
            workerInteractor.balancePopulateWork()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
            R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setConverterState(state: Boolean) {

        if (state) {
            converter_value.visibility = View.VISIBLE
        } else {
            converter_value.visibility = View.GONE
        }
    }

    private fun initializeDrawers() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    private fun initializeSettings() {

        val converterState = settingRepository.getConverter()
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

    private fun initializeFragments() {
        val topbar = TopbarFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_topbar, topbar)
        transaction.commit()
        showMain()
    }

    private fun setBottomNavigationController() {
        main_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mainFragment -> {
                    showMain()
                }
                R.id.costsFragment -> {
                    loadFragment(CostsFragment.newInstance())
                }
                R.id.chartFragment -> {
                    loadFragment(ChartFragment.newInstance())
                }
                R.id.limitsFragment -> {
                    loadFragment(LimitsFragment.newInstance(), withTopbar = false)
                }
                else -> {
                    throw Exception("uncorrect id")
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun loadFragment(
        fragment: Fragment,
        isAddedToBackstack: Boolean = false,
        withTopbar: Boolean = true,
        withBottomBar: Boolean = true,
        withAppBar: Boolean = false
    ) {
        if (withBottomBar) showBottomNav() else hideBottomNav()
        if (withTopbar) showDatePickerBar() else hideDatePickerBar()
        if (withAppBar) {
            supportActionBar!!.show()
        } else
            supportActionBar!!.hide()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (isAddedToBackstack) {
            transaction.addToBackStack(null)
        }
        transaction.replace(R.id.container_main, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this, "main activity")
    }

    override fun onBackPressed() {
        val current = supportFragmentManager.findFragmentById(R.id.container_main)
        if (current is KeyboardFragment) {
            moveTaskToBack(true)
        } else {
            showMain()
            main_navigation.selectedItemId = R.id.mainFragment
        }
    }

    fun showMain(bundle: Bundle? = null) {
        val main = KeyboardFragment.newInstance(bundle)
        loadFragment(main, withTopbar = false, withAppBar = true)
    }

    fun showCategoryPicker() {
        val fragment = CategoryPickerFragment.newInstance()
        loadFragment(fragment, withTopbar = false, withBottomBar = false)
    }

    private fun showDatePickerBar() {
        container_topbar.showDelayed()
    }

    private fun showBottomNav() {
        main_navigation.showDelayed()
    }

    private fun hideBottomNav() {
        main_navigation.hideDelayed()
    }

    private fun hideDatePickerBar() {
        container_topbar.hideDelayed()
    }
}

package com.example.holmi_production.money_counter_app.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.findBehavior
import com.example.holmi_production.money_counter_app.storage.AppPreference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_menu_currency_converting.*
import kotlinx.android.synthetic.main.include_menu_end_period_date.*
import kotlinx.android.synthetic.main.menu_drawer_custom.*
import org.koin.android.ext.android.get
import ru.semper_viventem.backdrop.BackdropBehavior

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ARG_LAST_MENU_ITEM = "last_menu_item"

        private const val MENU_DASHBOARD = R.id.dashboardFragment
        private const val MENU_TRANSACTIONS = R.id.transactionFragment
        private const val MENU_CHARTS = R.id.chartFragment
        private const val MENU_LIMITS = R.id.limitsFragment

        private const val DEFAULT_ITEM =
            MENU_DASHBOARD
    }

//    private val workerInteractor: WorkerInteractor by inject()

    lateinit var appBarConfiguration: AppBarConfiguration

    val navController by lazy {
        val host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        host.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavController()
        initializeBackdrop()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.onBoardingFragment) {
                backLayout.visibility = View.GONE
            } else {
                backLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ARG_LAST_MENU_ITEM, navigationView.checkedItem!!.itemId)

        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }

    private fun initializeBackdrop() {
        val backdropBehavior: BackdropBehavior = navHostFragment.findBehavior()
        with(backdropBehavior) {
            attachBackLayout(R.id.backLayout)
        }
        navigationView.setCheckedItem(DEFAULT_ITEM)
        navigationView.setNavigationItemSelectedListener { item ->
            navigationView.setCheckedItem(item.itemId)
            checkMenuPosition(item.itemId)
            if(navController.popBackStack(item.itemId, false).not()){
                navController.navigate(item.itemId)
            }
            backdropBehavior.close()
            true
        }
    }

    private fun initNavController() {
        val mainGraph = navController.navInflater.inflate(R.navigation.main_graph)
        val appPrefs: AppPreference = get()
        if (appPrefs.isOnboardingCompleted.not()) {
            val onBoardingGraph = navController.navInflater.inflate(R.navigation.onboarding_graph)
            mainGraph.addAll(onBoardingGraph)
            mainGraph.startDestination = R.id.onBoardingFragment
        } else {
            mainGraph.startDestination = R.id.dashboardFragment
        }
        navController.graph = mainGraph
    }

        private fun checkMenuPosition(@IdRes menuItemId: Int) {
            when (menuItemId) {
                MENU_DASHBOARD -> {
                    R.id.dashboardFragment
                }
                MENU_TRANSACTIONS -> {
                    R.id.transactionFragment
                }
                MENU_CHARTS -> {
                    R.id.chartFragment
                }
                MENU_LIMITS -> {
                    R.id.limitsFragment
                }
                else -> {
                    R.id.dashboardFragment
                }
            }
        }


//        initializeSettings()
//        initView()


        private fun initView() {
            et_end_month_value.text = ""
            //settings listener
            end_month_container.setOnClickListener {
                val b = AlertDialog.Builder(this)
                b.setTitle("День окончания месяца")
                val datas = IntArray(28).map { it.toString() }
//                b.setItems(datas) { dialog, which ->
//                    Log.d("M_SettingsFragment", "id: $which ${datas[which]}")
////                workerInteractor.startEndMonthWorker(datas[which].toInt())
//                    et_end_month_value.text = datas[which].toString()
//                    dialog!!.dismiss()
//                }
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
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.dashboardFragment,
//                R.id.transactionFragment,
//                R.id.limitsFragment,
//                R.id.chartFragment
//            ), drawer
//
//        )
            NavigationUI.setupWithNavController(navigationView, navController)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return item.onNavDestinationSelected(findNavController(R.id.navHostFragment))
                    || super.onOptionsItemSelected(item)
        }

        override fun onSupportNavigateUp(): Boolean {
            val navController = findNavController(R.id.navHostFragment)
            return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }
//
//    private fun initializeSettings() {
//
//        val converterState = appPreference.isConverterEnabled
//        val defValue = appPreference.getConverterValue()
//        converter_value.text = Editable.Factory.getInstance().newEditable(defValue)
//        converter_checkbox.isChecked = converterState
//        setConverterState(converterState)
//        converter_checkbox.setOnCheckedChangeListener { _, isChecked ->
//            setConverterState(isChecked)
//            appPreference.isConverterEnabled = isChecked
//        }
//        converter_value.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                appPreference.setConverterValue(s.toString())
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//        })
//    }


    }

package br.com.usemobile.baseactivity.kotlin.core.platform

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.usemobile.baseactivity.kotlin.R
import br.com.usemobile.baseactivity.kotlin.core.extension.activityLogin
import br.com.usemobile.baseactivity.kotlin.core.extension.activityMenu
import br.com.usemobile.baseactivity.kotlin.core.extension.empty
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.progress_bar.*

import kotlinx.android.synthetic.main.toolbar.*

/**
 * Base Activity class.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity(private val childActivityName: String = String.empty()) : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    lateinit var navController: NavController
    lateinit var navDestination: NavDestination
    private var drawerLayout: DrawerLayout? = null


    private var doubleBackToExit: Boolean = false
    lateinit var progressBar: ProgressBar

    open fun navHostFragment() : Int = R.id.base_atv_nav_host_fragment

    abstract fun toolbarTitle() : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpActv()

    }

    private fun setUpActv() {
        when (childActivityName) {
            activityLogin -> {
//                setContentView(R.layout.activity_login)
//                shouldAddFragmentToContainer = false
            }
//            activityMenu -> {
//                setContentView(R.layout.menu_activity)
//                setSupportActionBar(menu_toolbar)
//                shouldAddFragmentToContainer = false
//            }
            else -> {
                this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave)
                setContentView(R.layout.activity_base)
                setSupportActionBar(toolbar_geral)
                img_home?.setOnClickListener { onBackPressed() }
            }
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setUpNavControllerAndAppbar()
        setupActionBar(navController, appBarConfiguration)
        progressBar =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) menu_frag_progress_bar else menu_frag_progress_bar_api21
        title_toolbar.text = toolbarTitle()

    }

    private fun setUpNavControllerAndAppbar() {
        navController = Navigation.findNavController(this, navHostFragment())
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navController.addOnDestinationChangedListener{controller, destination, arguments ->
            navDestination = destination
//            setUpStatusBar()
//            setUpButton()
        }

    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig: AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {

        this.overridePendingTransition(R.anim.animation_enterback, R.anim.animation_back)
        when {
            doubleBackToExit -> super.onBackPressed()
            childActivityName != activityMenu && childActivityName != activityLogin -> super.onBackPressed()
            else -> handleBackPressed()
        }
        Handler().postDelayed({ doubleBackToExit = false }, 2000)
    }

    private fun handleBackPressed() {

        when (navDestination.id) {
            R.id.mainFragment -> {
                showMessage(getString(R.string.title_press_again_to_exit), false)
                this.doubleBackToExit = true
            }
            else -> super.onBackPressed()
        }
    }

    fun showMessage(message: String, isErrorMessage: Boolean = false) {

        if (isErrorMessage) {
            Toasty.error(this, message).show()
        } else {

            Toasty.success(this, message).show()
        }
    }

}

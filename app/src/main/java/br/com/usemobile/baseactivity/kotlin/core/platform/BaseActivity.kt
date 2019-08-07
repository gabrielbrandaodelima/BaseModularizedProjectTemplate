package br.com.usemobile.baseactivity.kotlin.core.platform

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import br.com.usemobile.baseactivity.kotlin.R
import br.com.usemobile.baseactivity.kotlin.core.extension.activityLogin
import br.com.usemobile.baseactivity.kotlin.core.extension.activityMenu
import br.com.usemobile.baseactivity.kotlin.core.extension.empty
import com.google.android.material.snackbar.Snackbar
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.progress_bar.*

import kotlinx.android.synthetic.main.toolbar.*

/**
 * Base Navigation Activity class,
 * to do initial activities setups and handle navigation.
 *@param childActivityName String for identifying activity layout template to inflate from
 * @see AppCompatActivity
 * @see setUpActv
 */
abstract class BaseActivity(private val childActivityName: String = String.empty()) : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var navDestination: NavDestination
    private var drawerLayout: DrawerLayout? = null


    private var doubleBackToExit: Boolean = false
    lateinit var progressBar: ProgressBar

    open fun navHostFragment(): Int = R.id.base_atv_nav_host_fragment

    /**
     * - Resource *id* identifier of your main nav graph start destination, for handling onBackPressed behavior
     *  > Ex:
     *
     *   >*R.id.home_fragment_dest*
     */
    abstract fun navGraphStartDestination(): Int

    abstract fun toolbarTitle(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpActv()

    }

    /**
     * - Used for setting up activity layout based on constructor's [childActivityName] param.
     */
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
        progressBar =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) menu_frag_progress_bar else menu_frag_progress_bar_api21
        title_toolbar.text = toolbarTitle()

    }

    /**
     * - Set up Activity's Navigation Controller and it's destination changed listener
     */
    private fun setUpNavControllerAndAppbar() {
        navController = Navigation.findNavController(this, navHostFragment())
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navController.addOnDestinationChangedListener { controller, destination, bundle ->
            onDestinationChangedListener(controller, destination, bundle)
        }

    }

    /**
     * - Override on activity which extends [BaseActivity],
     * to handle nav controller destination changed
     */
    open fun onDestinationChangedListener(
        controller: NavController,
        destination: NavDestination,
        bundle: Bundle?
    ) {
        navDestination = destination
    }

    /**
     * - Needed only when you have settled up action bar using native action bar settings,
     * in order to handle Action Bar navigation.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {

        this.overridePendingTransition(R.anim.animation_enterback, R.anim.animation_back)
        when {
            doubleBackToExit -> finish()
            childActivityName != activityMenu && childActivityName != activityLogin -> super.onBackPressed()
            else -> handleBackPressed()
        }
        Handler().postDelayed({ doubleBackToExit = false }, 2000)
    }

    /**
     * - Handle Activities double on back pressed clicks.
     */
    private fun handleBackPressed() {

        when (navDestination.id) {
            navGraphStartDestination() -> {
                showMessage(getString(R.string.title_press_again_to_exit), false)
                this.doubleBackToExit = true
            }
            else -> super.onBackPressed()
        }
    }

    /**
     * - Notify using Snackbar
     * @param message String msg
     */
    fun notify(@StringRes message: String) =
        Snackbar.make(actv_view, message, Snackbar.LENGTH_SHORT).show()

    /**
     * - Display a Toasty message
     * @param message String msg
     * @param isErrorMessage set to true if is an error
     */
    fun showMessage(message: String, isErrorMessage: Boolean = false) {

        if (isErrorMessage) {
            Toasty.error(this, message).show()
        } else {

            Toasty.success(this, message).show()
        }
    }

    /**
     *
     * - Used to navigate to destination Id handling the Navigation Controller
     * @param id Resource Identifier to navigate to
     * > Ex. *R.id.destination* or *R.id.action*
     *
     */
    fun navigateToDestinationRes(@IdRes id: Int) {
        findNavController(navHostFragment()).navigate(id)
    }

    /**
     * - Used to navigate to destination with action handling the Navigation Controller
     * @param directions NavDirections action to navigate to.
     * - Example:
     * > *MainFragmentDirections.nextAction()*
     */
    fun navigateWithActionToRes(directions: NavDirections) {
        findNavController(navHostFragment()).navigate(directions)
    }

    /**
     * - Creates a click listener to resource Id using Navigation arch components
     * @param id Resource Identifier to navigate to.
     * - Ex.
     *
     * >*R.id.destination* or *R.id.action*
     * @return Click listener of type [Navigation.createNavigateOnClickListener]
     * @throws java.lang.IllegalStateException When view does not have a NavController set
     */
    fun createNavigateToIdResClickListener(@IdRes id: Int): View.OnClickListener {
        return Navigation.createNavigateOnClickListener(id)
    }

    /**
     * - Sets the Navigation Controller to specified view for later use of [createNavigateToIdResClickListener].
     * @param view View to attach navController to.
     *
     * - Example:
     *  > setViewNavController(*floatingActionButton2*)
     *  > floatingActionButton2.setOnClickListener(*createNavigateToIdResClickListener(R.id.flow_step_two_dest)*)
     */
    fun setViewNavController(view: View) {
        Navigation.setViewNavController(view, navController)
    }
}

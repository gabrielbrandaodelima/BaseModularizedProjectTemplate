/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.usemobile.baseactivity.kotlin.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.usemobile.baseactivity.kotlin.R
import br.com.usemobile.baseactivity.kotlin.core.exception.Failure
import br.com.usemobile.baseactivity.kotlin.core.extension.activityMenu
import br.com.usemobile.baseactivity.kotlin.core.extension.empty
import br.com.usemobile.baseactivity.kotlin.core.extension.gone
import br.com.usemobile.baseactivity.kotlin.core.extension.visible
import com.google.android.material.snackbar.Snackbar
import es.dmoral.toasty.Toasty


/**
 * - Base Navigation Fragment class which extends [BaseFragment] with helper methods for setting up Navigation Architecture Components and handle it's navigation events.
 *@param childFragmentName Child fragment's name for identification
 * @see BaseFragment
 */
abstract class BaseNavigationFragment(private val childFragmentName: String = String.empty()) :
    BaseFragment(childFragmentName) {

    /**
     * - Child fragment's NavController , initiate it with :
     * > *activity?.let { Navigation.findNavController(it, [navHostFragment]) }*
     */
    abstract fun navController(): NavController?
    private var drawerLayout: DrawerLayout? = null

    /**
     * - NavOptions for using when passing a direct destination id using [navigateToDestinationRes]
     */
    val defNavOptions = navOptions {
        anim {
            enter = R.anim.nav_default_enter_anim
            exit = R.anim.nav_default_exit_anim
            popEnter = R.anim.nav_default_pop_enter_anim
            popExit = R.anim.nav_default_pop_exit_anim

        }
    }

    /**
     * Fragment's navHostFragment parent which will handle navigation
     */
    abstract fun navHostFragment(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavController()
    }

    /**
     * - Set up Fragment's Navigation Controller and it's destination changed listener
     */
    private fun setUpNavController() {
        navController()?.addOnDestinationChangedListener { controller, destination, arguments ->
            activity?.let {
                when (it) {
                    is BaseActivity -> it.navDestination = destination
                }
            }
            onDestinationChangedListener(controller, destination, arguments)
        }
    }

    /**
     * - Override on activity which extends [BaseActivity],
     * to handle nav controller destination changed
     */
    abstract fun onDestinationChangedListener(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    )

    /**
     * - Sets the Navigation Controller to specified view for later use of [createNavigateToIdResClickListener],[navigateWithActionToRes] or [navigateToDestinationRes].
     * @param view View to attach navController to.
     *
     * - Example:
     *  > setViewNavController(*floatingActionButton2*)
     *  > floatingActionButton2.setOnClickListener(*createNavigateToIdResClickListener(R.id.flow_step_two_dest)*)
     */
    fun setViewNavController(view: View) {
        Navigation.setViewNavController(view, navController())
    }

    /**
     * - Used to navigate to destination with action handling the Navigation Controller
     * @param directions NavDirections action to navigate to.
     *
     * - Example:
     * > *MainFragmentDirections.nextAction()*
     */
    fun navigateWithActionToRes(navController: NavController?, directions: NavDirections) {
        navController?.navigate(directions)
    }

    /**
     *
     * - Used to navigate to destination Id handling the Navigation Controller
     * @param navController NavController handler
     * @param id Resource Identifier to navigate to.
     *
     * - Ex.
     * > *R.id.destination* or *R.id.action*
     * @param args Arguments to pass and retrieve using safeArgs later
     * @param navOptions [NavOptions] to use in navigation, such as animations.
     *
     * - Ex:
     *
    val options = navOptions {

    anim {

         > enter = R.anim.slide_in_right

    > exit = R.anim.slide_out_left

    > popEnter = R.anim.slide_in_left

    > popExit = R.anim.slide_out_right

    >}

    }
     *
     */
    fun navigateToDestinationRes(navController: NavController?, @IdRes id: Int, args: Bundle? = null , navOptions: NavOptions? = null) {
        navController?.navigate(id, args, navOptions)
    }

    /**
     * - Creates a click listener to resource Id using Navigation arch components
     * @param id Resource Identifier to navigate to.
     *
     * - Ex.
     * > *R.id.destination* or *R.id.action*
     * @return Click listener of type Navigation.createNavigateOnClickListener
     * @see setViewNavController
     */
//    fun createNavigateToIdResClickListener(@IdRes id: Int): View.OnClickListener {
//        return Navigation.createNavigateOnClickListener(id)
//    }
}

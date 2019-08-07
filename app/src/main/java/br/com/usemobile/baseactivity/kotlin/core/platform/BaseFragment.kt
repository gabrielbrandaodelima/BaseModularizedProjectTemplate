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
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
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
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment(private val childFragmentName: String = String.empty()) : Fragment() {

    private var navController: NavController? = null
    private lateinit var navDestination: NavDestination
    private var drawerLayout: DrawerLayout? = null

    abstract fun layoutId(): Int

    open fun navHostFragment() : Int = R.id.base_atv_nav_host_fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavController()
    }

    open fun onBackPressed() {
    }

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun showProgressBar() = with(activity) {
        if (this is BaseActivity)
            progressBar.visible()
    }

    internal fun hideProgressBar() = with(activity) {
        if (this is BaseActivity)
            progressBar.gone()
    }

    /**
     * - Set up Fragment's Navigation Controller and it's destination changed listener
     */
    private fun setUpNavController() {
        navController = activity?.let { Navigation.findNavController(it, navHostFragment()) }
        navController?.addOnDestinationChangedListener{controller, destination, arguments ->
            onDestinationChangedListener(controller, destination, arguments)
        }
    }
    /**
     * - Override on activity which extends [BaseActivity],
     * to handle nav controller destination changed
     */
    open fun onDestinationChangedListener(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        navDestination = destination
    }

    /**
     *
     * - Used to navigate to destination Id handling the Navigation Controller
     * @param id Resource Identifier to navigate to.
     *
     * - Ex.
     * > *R.id.destination* or *R.id.action*
     *
     */
    fun navigateToDestinationRes(@IdRes id: Int) {
        findNavController().navigate(id)
    }
    /**
     * - Used to navigate to destination with action handling the Navigation Controller
     * @param directions NavDirections action to navigate to.
     *
     * - Example:
     * > *MainFragmentDirections.nextAction()*
     */
    fun navigateWithActionToRes(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    /**
     * - Creates a click listener to resource Id using Navigation arch components
     * @param id Resource Identifier to navigate to; ex. R.id.destination or R.id.action
     * @return Click listener of type Navigation.createNavigateOnClickListener
     */
    fun createNavigateToIdResClickListener(@IdRes id: Int): View.OnClickListener {
        return Navigation.createNavigateOnClickListener(id)
    }

    /**
     * - Method for handling viewModel's endpoints call failures.
     *
     * @param failure Failure of type [Failure]
     */
    fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                when (childFragmentName) {
                    activityMenu -> showMessage("NetworkConnectionError", true)
                    else -> handleNetworkError()
                }
            }
            is Failure.ServerError -> showMessage("ServerError", true)
            is Failure.FeatureFailure -> handleFeatureFailure()
        }
//        hideAddPageProgress()
//        hideProgress()
    }

    private fun handleNetworkError() {
//        showConnectionErrorView()
    }

    /**
     * - Method for handling feature's specific failures.
     *
     * > *Override this for specific cases.*
     */
    open fun handleFeatureFailure() {
        showMessage("BaseFragment handleFeatureFailure", true)

    }

    /**
     * * Notify using Snackbar
     * @param message String msg
     */
    fun notify(@StringRes message: String) =
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }

    /**
     * - Display a Toasty message
     * @param message String msg
     * @param isErrorMessage Set to true if is an error
     */
    fun showMessage(message: String, isErrorMessage: Boolean = false) {

        if (isErrorMessage) {
            activity?.let { Toasty.error(it, message).show() }
        } else {

            activity?.let { Toasty.success(it, message).show() }
        }
    }
}

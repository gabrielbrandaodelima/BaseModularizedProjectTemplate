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
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpNavControllerAndAppbar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)


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

    private fun setUpNavControllerAndAppbar() {
        navController = activity?.let { Navigation.findNavController(it, navHostFragment()) }
        navController?.addOnDestinationChangedListener{controller, destination, arguments ->
            navDestination = destination
//            setUpStatusBar()
//            setUpButton()
        }

    }

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

    open fun handleFeatureFailure() {
        showMessage("BaseFragment handleFeatureFailure", true)

    }

    fun notify(@StringRes message: Int) =
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }

    fun showMessage(message: String, isErrorMessage: Boolean = false) {

        if (isErrorMessage) {
            activity?.let { Toasty.error(it, message).show() }
        } else {

            activity?.let { Toasty.success(it, message).show() }
        }
    }
}

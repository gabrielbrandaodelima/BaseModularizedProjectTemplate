/*
 * Copyright (C) 2018 The Android Open Source Project
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

package br.com.usemobile.baseactivity.kotlin.features

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import br.com.usemobile.baseactivity.kotlin.R
import br.com.usemobile.baseactivity.kotlin.core.extension.activityMenu
import br.com.usemobile.baseactivity.kotlin.core.platform.BaseFragment
import br.com.usemobile.baseactivity.kotlin.core.platform.BaseNavigationFragment
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Fragment used to show how to navigate to another destination
 */
class MainFragment : BaseNavigationFragment(activityMenu) {

    override fun layoutId() = R.layout.main_fragment

    override fun navHostFragment(): Int = R.id.main_frag_nav_host_fragment

    override fun navController(): NavController? = activity?.let { Navigation.findNavController(it, navHostFragment()) }

    var action: NavDirections = HomeFragmentDirections.nextAction()
    var flowViewModel: FlowViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flowViewModel = activity?.let { ViewModelProviders.of(it).get(FlowViewModel::class.java) }

        floatingActionButton2.setOnClickListener{
            navigateWithActionToRes(navController(), action)
        }

//        setViewNavController(floatingActionButton2)
//        floatingActionButton2.setOnClickListener(
//            createNavigateToIdResClickListener(
//                R.id.next_action
//            )
//        )


    }

    override fun onDestinationChangedListener(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        flowViewModel?.navDestination?.value = destination
        when (destination.id) {
            R.id.home_fragment_dest -> action = HomeFragmentDirections.nextAction()
//            R.id.mainFragment -> action = MainFragmentDirections.nextActionFrag2()
            R.id.flow_step_one_dest, R.id.flow_step_two_dest -> action = FlowStepFragmentDirections.nextAction()

        }
    }
}

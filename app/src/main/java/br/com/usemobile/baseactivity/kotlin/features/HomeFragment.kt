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
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import br.com.usemobile.baseactivity.kotlin.R
import br.com.usemobile.baseactivity.kotlin.core.platform.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*

/**
 * Fragment used to show how to navigate to another destination
 */
class HomeFragment : BaseFragment() {
    override fun navController(): NavController? {
        return activity?.let { Navigation.findNavController(it, navHostFragment()) }
    }

    override fun onDestinationChangedListener(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }

    override fun navHostFragment(): Int = R.id.main_frag_nav_host_fragment
    override fun layoutId() = R.layout.home_fragment

//    var action: NavDirections =

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        navigate_destination_button.setOnClickListener(createNavigateToIdResClickListener(R.id.next_action))

        navigate_action_button.setOnClickListener {
//                        notify("test")
            navigateToDestinationRes(R.id.flow_step_one_dest)
        }

    }
}

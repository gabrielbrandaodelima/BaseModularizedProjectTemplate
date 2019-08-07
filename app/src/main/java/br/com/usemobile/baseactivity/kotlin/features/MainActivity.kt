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
import androidx.navigation.*
import br.com.usemobile.baseactivity.kotlin.R
import br.com.usemobile.baseactivity.kotlin.core.extension.activityMenu
import br.com.usemobile.baseactivity.kotlin.core.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*


/**
 * A simple activity demonstrating use of a NavHostFragment with a navigation drawer.
 */
class MainActivity : BaseActivity(activityMenu) {
    lateinit var action: NavDirections
    override fun toolbarTitle(): String = "Main"

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
//        floatingActionButton2.setOnClickListener {
//            navigateWithActionToRes(action)
//            notify("test")
//        }
        setViewNavController(floatingActionButton2)
        floatingActionButton2.setOnClickListener(createNavigateToIdResClickListener(R.id.next_action))
    }

    override fun onDestinationChangedListener(controller: NavController, destination: NavDestination, bundle: Bundle?) {
        super.onDestinationChangedListener(controller, destination, bundle)
        when (destination.id) {
            R.id.mainFragment -> action = MainFragmentDirections.nextAction()
//            R.id.mainFragment -> action = MainFragmentDirections.nextActionFrag2()
            R.id.flow_step_one_dest, R.id.flow_step_two_dest -> action = FlowStepFragmentDirections.nextAction()

        }
    }

}

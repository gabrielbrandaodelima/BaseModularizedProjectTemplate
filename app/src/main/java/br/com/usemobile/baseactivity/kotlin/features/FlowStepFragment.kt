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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.usemobile.baseactivity.kotlin.R
import br.com.usemobile.baseactivity.kotlin.core.platform.BaseFragment
import kotlinx.android.synthetic.main.flow_step_one_fragment.*

/**
 * Presents how multiple steps flow could be implemented.
 */
class FlowStepFragment : BaseFragment() {
    override fun layoutId(): Int {

        val safeArgs: FlowStepFragmentArgs by navArgs()
        val flowStepNumber = safeArgs.flowStepNumber
        return when (flowStepNumber) {
            2 -> R.layout.flow_step_two_fragment
            else -> R.layout.flow_step_one_fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        next_button.setOnClickListener {
//            findNavController().navigate(R.id.mainFragment)
//        }
        next_button.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.next_action)
        )
    }
}

package br.com.usemobile.baseactivity.kotlin.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDestination
import br.com.usemobile.baseactivity.kotlin.core.platform.BaseViewModel


/** Created by Gabriel
 * on 07/08/2019
 * at 15:17
 * at Usemobile
 **/

class FlowViewModel : BaseViewModel() {
    var navDestination = MutableLiveData<NavDestination>()



}
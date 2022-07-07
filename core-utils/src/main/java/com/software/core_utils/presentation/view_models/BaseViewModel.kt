package com.software.core_utils.presentation.view_models

import androidx.lifecycle.ViewModel
import com.software.core_utils.presentation.common.Action
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

open class BaseViewModel: ViewModel() {

    protected val _action = Channel<Action>()
    var action: Flow<Action> =
        _action.receiveAsFlow()
}
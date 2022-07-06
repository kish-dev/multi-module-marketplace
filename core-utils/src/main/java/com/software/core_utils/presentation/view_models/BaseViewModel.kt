package com.software.core_utils.presentation.view_models

import androidx.lifecycle.ViewModel
import com.software.core_utils.presentation.common.ActionState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

open class BaseViewModel: ViewModel() {

    protected val _action = Channel<ActionState<String>>()
    var action: Flow<ActionState<String>> =
        _action.receiveAsFlow()
}
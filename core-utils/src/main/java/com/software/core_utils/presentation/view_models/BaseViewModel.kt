package com.software.core_utils.presentation.view_models

import androidx.lifecycle.ViewModel
import com.software.core_utils.presentation.common.Action
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel: ViewModel() {

    protected val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action> = _action
}
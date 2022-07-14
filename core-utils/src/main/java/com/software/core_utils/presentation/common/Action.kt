package com.software.core_utils.presentation.common

import androidx.annotation.StringRes

sealed class Action {
    class ShowToast(@StringRes val stringId: Int): Action()
}


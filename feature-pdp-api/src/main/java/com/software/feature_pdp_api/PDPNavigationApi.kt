package com.software.feature_pdp_api

import androidx.fragment.app.Fragment

interface PDPNavigationApi {
    fun isClosed(fragment: Fragment): Boolean
}
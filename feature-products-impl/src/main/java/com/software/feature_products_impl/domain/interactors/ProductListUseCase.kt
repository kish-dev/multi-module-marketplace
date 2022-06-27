package com.software.feature_products_impl.domain.interactors

import androidx.work.WorkInfo
import com.software.core_utils.models.DomainWrapper
import com.software.feature_api.wrappers.ServerResponse
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import kotlinx.coroutines.flow.Flow

interface ProductListUseCase {
    suspend fun getProducts(): DomainWrapper<List<ProductInListVO>>
    suspend fun addViewToProductInList(guid: String): DomainWrapper<ProductInListVO>
}
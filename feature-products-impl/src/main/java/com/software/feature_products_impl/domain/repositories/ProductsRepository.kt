package com.software.feature_products_impl.domain.repositories

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import androidx.work.Worker
import com.software.core_utils.models.ProductInListDTO
import com.software.core_utils.models.ServerResponse
import com.software.core_utils.repository_models.RepositoryResponse
import com.software.feature_products_impl.di.modules.RepositoryModule
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun getProducts(): RepositoryResponse<List<ProductInListDTO>?, String>

    suspend fun addViewToProductInList(guid: String): RepositoryResponse<ProductInListDTO?, String>

}

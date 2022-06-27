package com.software.feature_add_product_impl.data

import com.software.feature_add_product_impl.domain.repositories.AddProductRepository
import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ServerResponse
import com.software.storage_api.SharedPreferencesApi
import javax.inject.Inject

class AddProductRepositoryImpl @Inject constructor(
    private val sharedPreferencesApi: SharedPreferencesApi
) :
    AddProductRepository {

    override suspend fun addProduct(productDTO: ProductDTO): ServerResponse<Boolean> =
        when (val result = sharedPreferencesApi.insertNewProduct(productDTO)) {
            true -> {
                ServerResponse.Success(result)
            }

            false -> {
                ServerResponse.Error(NullPointerException("Can't add twice"))
            }
        }
}
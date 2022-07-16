package com.software.feature_add_product_impl.data

import com.software.feature_add_product_impl.domain.repositories.AddProductRepository
import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ServerResponse
import com.software.storage_api.SharedPreferencesApi
import javax.inject.Inject

class AddProductRepositoryImpl @Inject constructor(
    private val sharedPreferencesApi: SharedPreferencesApi
) : AddProductRepository {

    override suspend fun addProduct(productDTO: ProductDTO): ServerResponse<Boolean> =
        when (val result = sharedPreferencesApi.insertNewProduct(productDTO)) {
            true -> {

                ServerResponse.Success(result)
            }

            false -> {
                ServerResponse.Error(NullPointerException("Can't add twice"))
            }
        }

    override suspend fun restoreProduct(): ServerResponse<ProductDTO> =
        when (val result = sharedPreferencesApi.getLastSavedDraft()) {
            null -> {
                ServerResponse.Error(NullPointerException("Can't restore draft"))
            }

            else -> {
                ServerResponse.Success(result)
            }
        }

    override fun getLastSavedDraft(): ProductDTO? =
        sharedPreferencesApi.getLastSavedDraft()

    override fun saveDraft(productDTO: ProductDTO) {
        sharedPreferencesApi.setDraft(productDTO)
    }

    override fun clearDraft() {
        sharedPreferencesApi.clearDraft()
    }

}

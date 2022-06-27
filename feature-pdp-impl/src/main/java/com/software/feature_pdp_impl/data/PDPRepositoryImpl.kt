package com.software.feature_pdp_impl.data

import com.software.core_utils.models.ProductDTO
import com.software.feature_api.wrappers.ServerResponse
import com.software.feature_pdp_impl.domain.repository.PDPRepository
import com.software.storage_api.SharedPreferencesApi
import javax.inject.Inject

class PDPRepositoryImpl @Inject constructor(
    private val sharedPreferencesApi: SharedPreferencesApi
) : PDPRepository {
    
    override suspend fun getProductById(guid: String): ServerResponse<ProductDTO> =
        when (val result = sharedPreferencesApi.getProductById(guid)) {
            null -> {
                ServerResponse.Error(NullPointerException("Cache have not productDTO with guid=$guid"))
            }

            else -> {
                ServerResponse.Success(result)
            }
        }

}

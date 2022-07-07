package com.software.feature_pdp_impl.data

import com.software.feature_api.wrappers.ProductDTO
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

    override suspend fun changeCount(guid: String, countDiff: Int): ServerResponse<ProductDTO> =
        when (val result = sharedPreferencesApi.changeProductCount(guid, countDiff)) {
            null -> {
                ServerResponse.Error(NullPointerException("Cache can't change cart count productDTO with guid=$guid"))
            }

            else -> {
                ServerResponse.Success(result)
            }
        }

}

package com.software.feature_pdp_impl.data

import com.software.core_utils.models.ProductDTO
import com.software.core_utils.models.ServerResponse
import com.software.feature_api.ProductsApi
import com.software.feature_pdp_impl.domain.repository.PDPRepository
import javax.inject.Inject

class PDPRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi
) : PDPRepository {
    override suspend fun getProductById(guid: String): ServerResponse<ProductDTO> =
        ServerResponse.Success(
            ProductDTO(
                additionalParams = mapOf(),
                availableCount = 10,
                count = 2,
                description = "Этот борщ сведёт вас с ума!",
                guid = "b5f5852b-3b8c-4857-9f53-ac5c2b6a3b2f",
                images = listOf(
                    "https://cdn1.ozone.ru/s3/multimedia-j/6131838211.jpg",
                    "https://gcdn.utkonos.ru/resample/500x500q90/images/photo/3395/3395961_1H.jpg?mtime=5f15b35d",
                ),
                isFavorite = false,
                isInCart = false,
                name = "Борщ по-домашнему",
                price = "199",
                rating = 4.6,
                weight = 0.4
            )
        )
//        productsApi.getProducts()

}
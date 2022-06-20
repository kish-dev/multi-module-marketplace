package com.software.core_network_impl.data

import com.software.core_network_impl.mock.productDTOs
import com.software.core_network_impl.mock.productInListDTOs
import com.software.feature_api.ProductsApi
import com.software.feature_api.models.ProductDTO
import com.software.feature_api.models.ProductInListDTO
import javax.inject.Inject

class ProductsApiImpl @Inject constructor(): ProductsApi {
    override suspend fun getProducts(): List<ProductInListDTO>? =
        productInListDTOs

    override suspend fun getProductById(guid: String): ProductDTO? =
        productDTOs.findLast {
            it.guid == guid
        }

    override suspend fun addViewToProductInList(guid: String): ProductInListDTO? {
        val productInList = productInListDTOs.firstOrNull { it.guid == guid }
        productInList?.apply {
            ++viewsCount
        }
        return productInList
    }

    override suspend fun addProduct(productDTO: ProductDTO): Boolean? {
        productDTOs.add(productDTO)
        productInListDTOs.add(productDTO.mapToProductListDTO())
        return true
    }
}
package ru.ozon.route256.homework2.data.repositoriesImpl

//import com.software.feature_api.models.ProductDTO
//import com.software.feature_api.models.ProductInListDTO
//import ru.ozon.route256.homework2.domain.repositories.ProductsRepository
//import com.software.core_utils.data.mappers.mapToProductListDTO
//
//class MockProductsRepositoryImpl : ProductsRepository {
//    override suspend fun getProducts(): List<com.software.feature_api.models.ProductInListDTO>? =
//        productInListDTOs
//
//
//    override suspend fun getProductById(guid: String): com.software.feature_api.models.ProductDTO? {
//        return productDTOs.firstOrNull { it.guid == guid }
//    }
//
//    override suspend fun addViewToProductInList(guid: String): com.software.feature_api.models.ProductInListDTO? {
//        val productInList = productInListDTOs.firstOrNull { it.guid == guid }
//        productInList?.apply {
//            ++viewsCount
//        }
//        return productInList
//    }
//
//    override suspend fun addProduct(productDTO: com.software.feature_api.models.ProductDTO): com.software.feature_api.models.ProductInListDTO {
//            productDTOs.add(productDTO)
//            productInListDTOs.add(productDTO.mapToProductListDTO())
//            return productInListDTOs.last()
//    }
//}
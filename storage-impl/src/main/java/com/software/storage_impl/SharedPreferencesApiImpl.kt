package com.software.storage_impl

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.software.core_utils.Constants.PRODUCTS_IN_LIST_SP
import com.software.core_utils.Constants.PRODUCTS_SP
import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.mapToProductInListDTO
import com.software.storage_api.SharedPreferencesApi
import com.software.storage_impl.mappers.*
import com.software.storage_impl.models.ProductEntity
import com.software.storage_impl.models.ProductInListEntity
import com.software.storage_impl.utils.JSONConverterProductsEntity
import com.software.storage_impl.utils.JSONConverterProductsInListEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedPreferencesApiImpl @Inject constructor(private val appContext: Context) :
    SharedPreferencesApi {

    companion object {
        private const val PRODUCTS = "${BuildConfig.LIBRARY_PACKAGE_NAME}.products"
        private const val PRODUCTS_IN_LIST = "${BuildConfig.LIBRARY_PACKAGE_NAME}.products_in_list"
    }

    private val gson = Gson()

    private val spProducts: SharedPreferences
        get() {
            return appContext.getSharedPreferences(PRODUCTS_SP, 0)
        }

    private val spProductInListEntity: SharedPreferences
        get() {
            return appContext.getSharedPreferences(PRODUCTS_IN_LIST_SP, 0)
        }

    private val listEntityProductsInList: MutableList<ProductInListEntity>?
        get() {
            val jsonList = spProductInListEntity.getString(PRODUCTS_IN_LIST, "")
            var listEntity: MutableList<ProductInListEntity>? = null
            jsonList?.let {
                listEntity = JSONConverterProductsInListEntity(gson)
                    .toProductInListEntityList(jsonList)?.toMutableList()
            }
            listEntity?.sortedBy { it.name }
            return listEntity
        }

    private val listEntityProducts: MutableList<ProductEntity>?
        get() {
            val jsonList = spProducts.getString(PRODUCTS, "")
            var listEntity: MutableList<ProductEntity>? = null
            jsonList?.let {
                listEntity =
                    JSONConverterProductsEntity(gson).toProductsListEntity(it)?.toMutableList()
            }
            listEntity?.sortedBy { it.name }
            return listEntity
        }

    override fun insertProductsDTO(productsDTO: List<ProductDTO>) {
        val result = JSONConverterProductsEntity(gson).fromProductsListEntity(
            mapProductsDTOtoProductsEntity(
                listDTO = productsDTO,
                listEntity = listEntityProducts
            )
        )
        spProducts.edit().putString(PRODUCTS, result).apply()
    }

    override fun insertProductsInListDTO(productsInListDTO: List<ProductInListDTO>) {
        val result = JSONConverterProductsInListEntity(gson).fromProductInListEntityList(
            mapProductsInListDTOtoProductsInListEntity(
                listDTO = productsInListDTO,
                listEntity = listEntityProductsInList
            )
        )
        spProductInListEntity.edit().putString(PRODUCTS_IN_LIST, result).apply()
    }

    override fun insertProductInListDTO(productInListDTO: ProductInListDTO): Boolean {
        val prevListEntity = listEntityProductsInList
        val result =
            addProductInListToListEntities(
                productInListDTO.mapToEntity(0, productInListDTO.isInCart),
                listEntity = prevListEntity
            )
        val jsonResult = JSONConverterProductsInListEntity(gson).fromProductInListEntityList(result)
        spProductInListEntity.edit().putString(PRODUCTS_IN_LIST, jsonResult).apply()
        return result.size > (prevListEntity?.size ?: 0)
    }

    override fun insertProductDTO(productDTO: ProductDTO): Boolean {
        val prevListEntity = listEntityProducts
        val result =
            addProductToListEntities(
                productDTO.mapToEntity(
                    productDTO.isInCart,
                    productDTO.count
                ),
                listEntity = prevListEntity
            )
        val jsonResult = JSONConverterProductsEntity(gson).fromProductsListEntity(result)
        spProducts.edit().putString(PRODUCTS, jsonResult).apply()
        return result.size > (prevListEntity?.size ?: 0)
    }

    override fun getProductsInListDTO(): List<ProductInListDTO>? {
        return listEntityProductsInList?.map { it.mapToDTO() }
    }

    override fun getProductsDTO(): List<ProductDTO>? {
        return listEntityProducts?.map { it.mapToDTO() }
    }

    override fun getProductById(guid: String): ProductDTO? {
        val list = listEntityProducts
        return list?.findLast { it.guid == guid }?.mapToDTO()
    }

    override fun addViewToProductInListDTO(guid: String): ProductInListDTO? {
        val prevListEntity = listEntityProductsInList
        prevListEntity?.findLast { it.guid == guid }?.let {
            it.viewsCount += 1
        }
        prevListEntity?.let {
            val newJsonList =
                JSONConverterProductsInListEntity(gson).fromProductInListEntityList(it)
            spProductInListEntity.edit().putString(PRODUCTS_IN_LIST, newJsonList).apply()
        }
        return prevListEntity?.findLast { it.guid == guid }?.mapToDTO()
    }

    override fun updateProductBucketState(guid: String, inCart: Boolean): ProductInListDTO? {
        val prevListProductsInListEntity = listEntityProductsInList
        val prevListProductsEntity = listEntityProducts
        when (inCart) {
            true -> {
                prevListProductsInListEntity?.findLast { it.guid == guid }
                    ?.let { productInListEntity ->
                        when (productInListEntity.isInCart) {
                            true -> {
                            }
                            false -> {
                                productInListEntity.isInCart = inCart
                                prevListProductsEntity?.findLast { it.guid == guid }?.let {
                                    it.isInCart = inCart
                                    it.count = 1
                                }
                            }
                        }
                    }
            }

            else -> {
                prevListProductsInListEntity?.findLast { it.guid == guid }
                    ?.let { productInListEntity ->
                        when (productInListEntity.isInCart) {
                            true -> {
                                productInListEntity.isInCart = inCart
                                prevListProductsEntity?.findLast { it.guid == guid }?.let {
                                    it.isInCart = inCart
                                    it.count = null
                                }
                            }
                            false -> {
                            }
                        }
                    }
            }
        }

        prevListProductsInListEntity?.let {
            val newJsonList =
                JSONConverterProductsInListEntity(gson).fromProductInListEntityList(it)
            spProductInListEntity.edit().putString(PRODUCTS_IN_LIST, newJsonList).apply()
        }

        prevListProductsEntity?.let {
            val newJsonList =
                JSONConverterProductsEntity(gson).fromProductsListEntity(it)
            spProducts.edit().putString(PRODUCTS, newJsonList).apply()
        }

        return prevListProductsInListEntity?.findLast { it.guid == guid }?.mapToDTO()
    }

    override fun insertNewProduct(productDTO: ProductDTO): Boolean {
        return insertProductDTO(productDTO) && insertProductInListDTO(productDTO.mapToProductInListDTO())
    }
}

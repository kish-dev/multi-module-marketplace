package com.software.storage_impl

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.software.core_utils.Constants.DRAFT_SP
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
class SharedPreferencesApiImpl @Inject constructor(private val appContext: Context, private val gson: Gson) :
    SharedPreferencesApi {

    companion object {
        private const val PRODUCTS = "${BuildConfig.LIBRARY_PACKAGE_NAME}.products"
        private const val PRODUCTS_IN_LIST = "${BuildConfig.LIBRARY_PACKAGE_NAME}.products_in_list"
        private const val DRAFT = "${BuildConfig.LIBRARY_PACKAGE_NAME}.draft"
    }

    private val spProducts: SharedPreferences
        get() = appContext.getSharedPreferences(PRODUCTS_SP, 0)


    private val spProductsInList: SharedPreferences
        get() = appContext.getSharedPreferences(PRODUCTS_IN_LIST_SP, 0)


    private val spDraft: SharedPreferences
        get() = appContext.getSharedPreferences(DRAFT_SP, 0)


    private val listEntityProductsInList: MutableList<ProductInListEntity>?
        get() {
            val jsonList = spProductsInList.getString(PRODUCTS_IN_LIST, "")
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

    private val entityDraft: ProductEntity?
        get() {
            val json = spDraft.getString(DRAFT, "")
            return JSONConverterProductsEntity(gson).toProductEntity(json)
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
        spProductsInList.edit().putString(PRODUCTS_IN_LIST, result).apply()
    }

    override fun insertProductInListDTO(productInListDTO: ProductInListDTO): Boolean {
        val prevListEntity = listEntityProductsInList
        val result =
            addProductInListToListEntities(
                productInListDTO.mapToEntity(0, productInListDTO.isInCart),
                listEntity = prevListEntity
            )
        val jsonResult = JSONConverterProductsInListEntity(gson).fromProductInListEntityList(result)
        spProductsInList.edit().putString(PRODUCTS_IN_LIST, jsonResult).apply()
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
            spProductsInList.edit().putString(PRODUCTS_IN_LIST, newJsonList).apply()
        }
        return prevListEntity?.findLast { it.guid == guid }?.mapToDTO()
    }

    override fun updateProductCartState(guid: String, inCart: Boolean): ProductInListDTO? {
        val productInList = listEntityProductsInList?.findLast { it.guid == guid }
        val product = listEntityProducts?.findLast { it.guid == guid }
        when (inCart) {
            true -> {
                if (productInList?.isInCart == false) {
                    changeCount(guid, 1)
                }
            }

            false -> {
                if (productInList?.isInCart == true) {
                    product?.count?.let { changeCount(guid, -it) }
                }
            }
        }
        return listEntityProductsInList?.findLast { it.guid == guid }?.mapToDTO()
    }

    override fun changeProductCount(guid: String, countDiff: Int): ProductDTO? {
        changeCount(guid, countDiff)
        return listEntityProducts?.findLast { it.guid == guid }?.mapToDTO()
    }

    private fun changeProductIsFavorite(guid: String, isFavorite: Boolean) {
        val listProducts = listEntityProducts
        listProducts?.findLast { it.guid == guid }?.let {
            it.isFavorite = isFavorite
        }
        val listProductsInList = listEntityProductsInList
        listProductsInList?.findLast { it.guid == guid }?.let {
            it.isFavorite = isFavorite
        }

        val jsonResult = JSONConverterProductsEntity(gson).fromProductsListEntity(listProducts)
        spProducts.edit().putString(PRODUCTS, jsonResult).apply()

        val jsonResultInList = JSONConverterProductsInListEntity(gson).fromProductInListEntityList(listProductsInList)
        spProductsInList.edit().putString(PRODUCTS_IN_LIST, jsonResultInList).apply()
    }

    override fun changeIsFavorite(guid: String, isFavorite: Boolean): ProductDTO? {
        changeProductIsFavorite(guid, isFavorite)
        val ret = listEntityProducts?.findLast { it.guid == guid }?.mapToDTO()
        return ret
    }

    override fun getLastSavedDraft(): ProductDTO? {
        return entityDraft?.mapToDTO()
    }

    override fun setDraft(productDTO: ProductDTO) {
        val newEntityDto = productDTO.mapToEntity(productDTO.isInCart, productDTO.count)
        val json = JSONConverterProductsEntity(gson).fromProductEntity(newEntityDto)
        spDraft.edit().putString(DRAFT, json).apply()
    }

    override fun clearDraft() {
        spDraft.edit().remove(DRAFT).apply()
    }

    private fun changeCount(guid: String, countDiff: Int) {
        val prevListProductsInListEntity = listEntityProductsInList
        val prevListProductsEntity = listEntityProducts
        when {
            countDiff > 0 -> {
                prevListProductsInListEntity?.findLast { it.guid == guid }
                    ?.let { productInListEntity ->
                        when (productInListEntity.isInCart) {
                            true -> {
                                prevListProductsEntity?.findLast { it.guid == guid }?.let {
                                    it.count?.let { count ->
                                        if(count + countDiff <= 1000 && it.availableCount != null && count + countDiff <= it.availableCount) {
                                            it.count = it.count?.plus(countDiff)
                                        }
                                    }

                                }
                            }
                            false -> {
                                prevListProductsEntity?.findLast { it.guid == guid }?.let {
                                    val count = it.count ?: 0
                                    if(it.availableCount != null && (count + countDiff) <= it.availableCount) {
                                        productInListEntity.isInCart = true
                                        it.isInCart = true
                                        it.count = countDiff
                                    }
                                }
                            }
                        }
                    }
            }

            countDiff < 0 -> {
                prevListProductsInListEntity?.findLast { it.guid == guid }
                    ?.let { productInListEntity ->
                        when (productInListEntity.isInCart) {
                            true -> {
                                prevListProductsEntity?.findLast { it.guid == guid }
                                    ?.let { productEntity ->
                                        if (productEntity.count == null) {
                                            productEntity.isInCart = false
                                            productInListEntity.isInCart = false
                                        } else {
                                            productEntity.count?.let { count ->
                                                if (count + countDiff > 0) {
                                                    productEntity.count?.let {
                                                        productEntity.count = it + countDiff
                                                    }
                                                } else {
                                                    productEntity.count = null
                                                    productInListEntity.isInCart = false
                                                    productEntity.isInCart = false
                                                }
                                            }
                                        }
                                    }
                            }
                            false -> {
                                prevListProductsEntity?.findLast { it.guid == guid }?.let {
                                    productInListEntity.isInCart = false
                                    it.isInCart = false
                                    it.count = null
                                }
                            }
                        }
                    }
            }

            else -> {

            }
        }

        prevListProductsInListEntity?.let {
            val newJsonList =
                JSONConverterProductsInListEntity(gson).fromProductInListEntityList(it)
            spProductsInList.edit().putString(PRODUCTS_IN_LIST, newJsonList).apply()
        }

        prevListProductsEntity?.let {
            val newJsonList =
                JSONConverterProductsEntity(gson).fromProductsListEntity(it)
            spProducts.edit().putString(PRODUCTS, newJsonList).apply()
        }
    }

    override fun insertNewProduct(productDTO: ProductDTO): Boolean {
        return insertProductDTO(productDTO) && insertProductInListDTO(productDTO.mapToProductInListDTO())
    }
}

package com.software.storage_impl

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.software.core_utils.models.ProductDTO
import com.software.core_utils.models.ProductInListDTO
import com.software.storage_api.SharedPreferencesApi
import com.software.storage_impl.mappers.mapProductsDTOtoProductsEntity
import com.software.storage_impl.mappers.mapProductsInListDTOtoProductsInListEntity
import com.software.storage_impl.mappers.mapToDTO
import com.software.storage_impl.models.ProductEntity
import com.software.storage_impl.models.ProductInListEntity
import com.software.storage_impl.utils.JSONConverterProductsEntity
import com.software.storage_impl.utils.JSONConverterProductsInListEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesApiImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SharedPreferencesApi {

    companion object {
        private const val PRODUCTS = "com.software.storage_impl.products"
        private const val PRODUCTS_IN_LIST = "com.software.storage_impl.products_in_list"
    }

    @SuppressLint("CommitPrefEdits")
    override fun insertProductsDTO(productsDTO: List<ProductDTO>) {
        val jsonList = sharedPreferences.getString(PRODUCTS, "")
        var listEntity: MutableList<ProductEntity>? = null
        jsonList?.let {
            listEntity = JSONConverterProductsEntity.toProductList(jsonList).toMutableList()
        }
        val result = JSONConverterProductsEntity.fromProductList(
            mapProductsDTOtoProductsEntity(
                listDTO = productsDTO,
                listEntity = listEntity
            )
        )
        sharedPreferences.edit().putString(PRODUCTS, result)
    }

    @SuppressLint("CommitPrefEdits")
    override fun insertProductsInListDTO(productsInListDTO: List<ProductInListDTO>) {
        val jsonList = sharedPreferences.getString(PRODUCTS_IN_LIST, "")
        var listEntity: MutableList<ProductInListEntity>? = null
        jsonList?.let {
            listEntity = JSONConverterProductsInListEntity
                .toProductInListDTOList(jsonList).toMutableList()
        }
        val result = JSONConverterProductsInListEntity.fromProductInListDTOList(
            mapProductsInListDTOtoProductsInListEntity(
                listDTO = productsInListDTO,
                listEntity = listEntity
            )
        )
        sharedPreferences.edit().putString(PRODUCTS_IN_LIST, result)
    }

    @SuppressLint("CommitPrefEdits")
    override fun insertProductInListDTO(productInListDTO: ProductInListDTO) {
        val jsonList = sharedPreferences.getString(PRODUCTS_IN_LIST, "")
        var listEntity: MutableList<ProductInListEntity>? = null
        jsonList?.let {
            listEntity = JSONConverterProductsInListEntity
                .toProductInListDTOList(jsonList).toMutableList()
        }
        val listDTO = mutableListOf(productInListDTO)
        val result = JSONConverterProductsInListEntity.fromProductInListDTOList(
            mapProductsInListDTOtoProductsInListEntity(
                listDTO = listDTO,
                listEntity = listEntity
            )
        )
        sharedPreferences.edit().putString(PRODUCTS_IN_LIST, result)
    }

    @SuppressLint("CommitPrefEdits")
    override fun insertProductDTO(productDTO: ProductDTO) {
        val jsonList = sharedPreferences.getString(PRODUCTS, "")
        var listEntity: MutableList<ProductEntity>? = null
        jsonList?.let {
            listEntity = JSONConverterProductsEntity
                .toProductList(jsonList).toMutableList()
        }
        val listDTO = mutableListOf(productDTO)
        val result = JSONConverterProductsEntity.fromProductList(
            mapProductsDTOtoProductsEntity(
                listDTO = listDTO,
                listEntity = listEntity
            )
        )
        sharedPreferences.edit().putString(PRODUCTS, result)
    }

    override fun getProductsInListDTO(): List<ProductInListDTO>? {
        val jsonList = sharedPreferences.getString(PRODUCTS_IN_LIST, "")
        var listEntity: MutableList<ProductInListEntity>? = null
        jsonList?.let {
            listEntity = JSONConverterProductsInListEntity
                .toProductInListDTOList(jsonList).toMutableList()
        }
        return listEntity?.map { it.mapToDTO() }
    }

    override fun getProductById(guid: String): ProductDTO? {
        val jsonList = sharedPreferences.getString(PRODUCTS, "")
        var listEntity: MutableList<ProductEntity>? = null
        jsonList?.let {
            listEntity = JSONConverterProductsEntity
                .toProductList(jsonList).toMutableList()
        }
        return listEntity?.findLast { it.guid == guid }?.mapToDTO()
    }
}
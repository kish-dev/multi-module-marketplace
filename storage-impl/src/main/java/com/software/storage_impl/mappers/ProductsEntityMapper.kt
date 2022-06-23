package com.software.storage_impl.mappers

import com.software.core_utils.models.ProductDTO
import com.software.core_utils.models.ProductInListDTO
import com.software.storage_impl.models.ProductEntity
import com.software.storage_impl.models.ProductInListEntity

fun ProductInListDTO.mapToEntity(views: Int): ProductInListEntity {
    return ProductInListEntity(
        guid,
        image,
        name,
        price,
        rating,
        isFavorite,
        isInCart,
        views
    )
}

fun ProductInListEntity.mapToDTO(): ProductInListDTO {
    return ProductInListDTO(
        guid,
        image,
        name,
        price,
        rating,
        isFavorite,
        isInCart,
        viewsCount
    )
}

fun ProductDTO.mapToEntity(): ProductEntity {
    return ProductEntity(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        images,
        weight,
        count,
        availableCount,
        additionalParams
    )
}

fun ProductEntity.mapToDTO(): ProductDTO {
    return ProductDTO(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        images,
        weight,
        count,
        availableCount,
        additionalParams
    )
}


/**
 * Method for map network data with cached data
 *
 * This method main purpose - save sorted data with don't changed viewsCount in cachedData
 *
 * Sorted data must be saved only, because network data order isn't guaranteed
 *
 * @param listDTO network data
 * @param listEntity cached data
 * */
fun mapProductsInListDTOtoProductsInListEntity(
    listDTO: List<ProductInListDTO>,
    listEntity: MutableList<ProductInListEntity>?
): List<ProductInListEntity> {
    listDTO.sortedBy { it.name }

    if(listEntity == null) {
        return listDTO.map { it.mapToEntity(it.viewsCount) }
    }

    var entityIndex = 0
    var i = 0
    while(listDTO.size > i) {
        if(listEntity[entityIndex].guid == listDTO[i].guid) {
            listEntity[entityIndex] = listDTO[i].mapToEntity(listEntity[entityIndex].viewsCount)
            ++i
        }
        ++entityIndex
    }
    return listEntity
}

/**
 * Method for map network data with cached data
 *
 * This method main purpose - save sorted data
 *
 * Sorted data must be saved only, because network data order isn't guaranteed
 *
 * @param listDTO network data
 * @param listEntity cached data
 * */
fun mapProductsDTOtoProductsEntity(
    listDTO: List<ProductDTO>,
    listEntity: MutableList<ProductEntity>?
): List<ProductEntity> {
    listDTO.sortedBy { it.name }

    if(listEntity == null) {
        return listDTO.map { it.mapToEntity() }
    }

    var entityIndex = 0
    var i = 0
    while(listDTO.size > i) {
        if(listEntity[entityIndex].guid == listDTO[i].guid) {
            listEntity[entityIndex] = listDTO[i].mapToEntity()
            ++i
        }
        ++entityIndex
    }
    return listEntity
}



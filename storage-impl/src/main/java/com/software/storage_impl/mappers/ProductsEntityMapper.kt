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

fun ProductEntity.mapToProductInListEntity(): ProductInListEntity {
    return ProductInListEntity(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        viewsCount = 0
    )
}

fun addProductInListToListEntities(
    productEntity: ProductInListEntity,
    listEntity: MutableList<ProductInListEntity>?
) : List<ProductInListEntity> =
    when(listEntity?.findLast { it.guid == productEntity.guid }) {
        null -> {
            var resultList = mutableListOf<ProductInListEntity>()
            if(listEntity == null) {
                resultList.add(productEntity)
            }
            listEntity?.let {
                resultList.addAll(listEntity)
                resultList.add(productEntity)
            }

            resultList.sortedBy { it.name }
        }

        else -> {
            listEntity
        }

    }

fun addProductToListEntities(
    productEntity: ProductEntity,
    listEntity: MutableList<ProductEntity>?
) : List<ProductEntity> =
    when(listEntity?.findLast { it.guid == productEntity.guid }) {
        null -> {
            var resultList = mutableListOf<ProductEntity>()
            if(listEntity == null) {
                resultList.add(productEntity)
            }
            listEntity?.let {
                resultList.addAll(listEntity)
                resultList.add(productEntity)
            }

            resultList.sortedBy { it.name }
        }

        else -> {
            listEntity
        }

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
    val uniqueCacheItems = listEntity?.filter { entity -> listDTO.none { it.guid == entity.guid } }
    return listDTO.map { it.mapToEntity(it.viewsCount) }.plus(uniqueCacheItems ?: emptyList()).sortedBy { it.name }
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
    val uniqueCacheItems = listEntity?.filter { entity -> listDTO.none { it.guid == entity.guid } }
    return listDTO.map { it.mapToEntity() }.plus(uniqueCacheItems ?: emptyList()).sortedBy { it.name }
}



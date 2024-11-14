package com.software.storage_impl.mappers

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.storage_impl.models.ProductEntity
import com.software.storage_impl.models.ProductInListEntity

fun ProductInListDTO.mapToEntity(views: Int, isInCart: Boolean): ProductInListEntity {
    return ProductInListEntity(
        guid,
        listOf(image),
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
        images.firstOrNull() ?: "",
        name,
        price,
        rating,
        isFavorite,
        isInCart,
        viewsCount
    )
}

fun ProductDTO.mapToEntity(isInCart: Boolean, count: Int? = null): ProductEntity {
    return ProductEntity(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        listOf(images),
        weight,
        count,
        availableCount,
        additionalParams ?: emptyMap()
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
        images.firstOrNull() ?: "",
        weight,
        count,
        availableCount,
        additionalParams
    )
}

fun ProductEntity.mapToProductInListEntity(): ProductInListEntity {
    return ProductInListEntity(
        guid,
        images,
        name,
        price,
        rating,
        isFavorite,
        isInCart,
        viewsCount = 0
    )
}

fun addProductInListToListEntities(
    productEntity: ProductInListEntity,
    listEntity: MutableList<ProductInListEntity>?
): List<ProductInListEntity> =
    when (listEntity?.findLast { it.guid == productEntity.guid }) {
        null -> {
            val resultList = mutableListOf<ProductInListEntity>()
            if (listEntity == null) {
                resultList.add(productEntity)
            }
            listEntity?.let {
                resultList.addAll(listEntity)
                resultList.add(productEntity)
            }

            resultList.sortedBy { it.name.lowercase() }
        }

        else -> {
            listEntity
        }

    }

fun addProductToListEntities(
    productEntity: ProductEntity,
    listEntity: MutableList<ProductEntity>?
): List<ProductEntity> =
    when (listEntity?.findLast { it.guid == productEntity.guid }) {
        null -> {
            val resultList = mutableListOf<ProductEntity>()
            if (listEntity == null) {
                resultList.add(productEntity)
            }
            listEntity?.let {
                resultList.addAll(listEntity)
                resultList.add(productEntity)
            }

            resultList.sortedBy { it.name.lowercase() }
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
    return listDTO.map { dto ->
        dto.mapToEntity(
            views = listEntity?.findLast { it.guid == dto.guid }?.viewsCount ?: 0,
            isInCart = listEntity?.findLast { it.guid == dto.guid }?.isInCart ?: false
        )
    }.plus(uniqueCacheItems ?: emptyList())
        .sortedBy { it.name.lowercase() }
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
    return listDTO.map { dto ->
        dto.mapToEntity(
            isInCart = listEntity?.findLast { it.guid == dto.guid }?.isInCart ?: false,
            count = listEntity?.findLast { it.guid == dto.guid }?.count
        )
    }.plus(uniqueCacheItems ?: emptyList())
        .sortedBy { it.name.lowercase() }
}



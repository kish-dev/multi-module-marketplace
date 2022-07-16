package com.software.feature_add_product_impl.domain.interactors

import com.software.core_utils.models.DomainWrapper
import com.software.core_utils.presentation.view_objects.ProductVO
import com.software.core_utils.presentation.view_objects.isNotBlank
import com.software.feature_add_product_impl.domain.mappers.mapToDTO
import com.software.feature_add_product_impl.domain.mappers.mapToVO
import com.software.feature_add_product_impl.domain.repositories.AddProductRepository
import com.software.feature_api.wrappers.ServerResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddProductInteractorImpl(
    private val addProductRepository: AddProductRepository,
    private val dispatcher: CoroutineDispatcher
) : AddProductUseCase {

    override suspend fun addProductToAllPlaces(product: ProductVO) =
        withContext(dispatcher) {
            when (val response = addProductRepository.addProduct(product.mapToDTO())) {
                is ServerResponse.Success -> {
                    addProductRepository.clearDraft()
                    DomainWrapper.Success(response.value)
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(response.throwable)
                }
            }
        }

    override suspend fun restoreProduct(): DomainWrapper<ProductVO> =
        withContext(dispatcher) {
            when (val response = addProductRepository.restoreProduct()) {
                is ServerResponse.Success -> {
                    DomainWrapper.Success(response.value.mapToVO())
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(response.throwable)
                }
            }
        }

    override suspend fun saveDraft(productVO: ProductVO) {
        withContext(dispatcher) {
            when (isProductChanged(productVO)) {
                true -> {
                    addProductRepository.saveDraft(productVO.mapToDTO())
                }
                false -> {
                }
            }
        }
    }

    private fun isProductChanged(productVO: ProductVO): Boolean {
        if (!productVO.isNotBlank()) {
            addProductRepository.clearDraft()
            return false
        }

        val lastSavedDraft = addProductRepository.getLastSavedDraft()
        return when {
            lastSavedDraft == null -> true
            lastSavedDraft.name != productVO.name -> true
            lastSavedDraft.price != productVO.price -> true
            lastSavedDraft.description != productVO.description -> true
            lastSavedDraft.images != productVO.images -> true
            lastSavedDraft.rating != productVO.rating -> true
            else -> false
        }
    }
}

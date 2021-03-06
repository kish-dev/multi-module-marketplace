package com.software.feature_pdp_impl.domain.interactors

import com.software.core_utils.models.DomainWrapper
import com.software.core_utils.presentation.view_objects.ProductVO
import com.software.feature_api.wrappers.ServerResponse
import com.software.feature_pdp_impl.domain.mapper.mapToVO
import com.software.feature_pdp_impl.domain.repository.PDPRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PDPInteractorImpl @Inject constructor(
    private val pdpRepository: PDPRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductDetailUseCase {
    override suspend fun getProductById(guid: String): DomainWrapper<ProductVO> =
        withContext(dispatcher) {
            when (val response = pdpRepository.getProductById(guid)) {
                is ServerResponse.Success -> {
                    DomainWrapper.Success(response.value.mapToVO())
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(response.throwable)
                }
            }
        }

    override suspend fun changeCount(guid: String, countDiff: Int, count: Int): DomainWrapper<ProductVO> =
        withContext(dispatcher) {
            when (val response = pdpRepository.changeCount(guid, countDiff)) {
                is ServerResponse.Success -> {
                    when(response.value.count == count + countDiff) {
                        true -> DomainWrapper.Success(response.value.mapToVO())
                        false -> DomainWrapper.Error(Exception("Count not changed"))
                    }
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(response.throwable)
                }
            }
        }

    override suspend fun changeIsFavorite(
        guid: String,
        isFavorite: Boolean
    ): DomainWrapper<ProductVO> =
        withContext(dispatcher) {
            when (val response = pdpRepository.changeIsFavorite(guid, isFavorite)) {
                is ServerResponse.Success -> {
                    DomainWrapper.Success(response.value.mapToVO())
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(response.throwable)
                }
            }
        }

}

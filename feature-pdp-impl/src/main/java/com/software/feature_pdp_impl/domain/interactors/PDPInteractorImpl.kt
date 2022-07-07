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

    override suspend fun changeCount(guid: String, countDiff: Int): DomainWrapper<ProductVO> =
        withContext(dispatcher) {
            when (val response = pdpRepository.changeCount(guid, countDiff)) {
                is ServerResponse.Success -> {
                    DomainWrapper.Success(response.value.mapToVO())
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(response.throwable)
                }
            }
        }

}

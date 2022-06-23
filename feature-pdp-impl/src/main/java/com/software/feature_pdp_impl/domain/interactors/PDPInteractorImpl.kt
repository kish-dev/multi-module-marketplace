package com.software.feature_pdp_impl.domain.interactors

import com.software.core_utils.models.ServerResponse
import com.software.core_utils.presentation.view_objects.ProductVO
import com.software.feature_pdp_impl.domain.mapper.mapToVO
import com.software.feature_pdp_impl.domain.repository.PDPRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PDPInteractorImpl @Inject constructor(
    private val pdpRepository: PDPRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductDetailUseCase {
    override suspend fun getProductById(guid: String): ServerResponse<ProductVO> =
        withContext(dispatcher) {
            when (val response = pdpRepository.getProductById(guid)) {
                is ServerResponse.Success -> {
                    ServerResponse.Success(response.value.mapToVO())
                }

                is ServerResponse.Error -> {
                    ServerResponse.Error(response.throwable)
                }
            }
        }
}

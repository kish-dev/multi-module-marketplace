package com.software.feature_pdp_impl.domain.interactors

import com.software.feature_pdp_impl.domain.mapper.mapToVO
import com.software.feature_pdp_impl.domain.repository.PDPRepository
import com.software.feature_pdp_impl.presentation.view_objects.ProductVO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PDPInteractorImpl @Inject constructor(
    private val pdpRepository: PDPRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductDetailUseCase {
    override suspend fun getProductById(guid: String): ProductVO? =
        withContext(dispatcher) {
            val product = pdpRepository.getProductById(guid)
            product?.let {
                return@withContext product.mapToVO()
            }
            return@withContext null
        }
}

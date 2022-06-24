package com.software.feature_products_impl.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.*
import com.software.core_utils.models.ProductInListDTO
import com.software.core_utils.models.ServerResponse
import com.software.core_utils.repository_models.RepositoryResponse
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import com.software.storage_api.SharedPreferencesApi
import com.software.workers.LoadProductsInListWorker
import com.software.workers.LoadProductsWorker
import com.software.workers.PRODUCTS_IN_LIST_RESPONSE
import com.software.workers.PRODUCTS_RESPONSE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val sharedPreferencesApi: SharedPreferencesApi,
    private val context: Context
) : ProductsRepository {

    private val workManager by lazy {
        WorkManager.getInstance(context)
    }

    override suspend fun getProducts(): RepositoryResponse<List<ProductInListDTO>?, String> {
        val requestProducts = OneTimeWorkRequest.Builder(
            LoadProductsWorker::class.java
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        val requestProductsInList = OneTimeWorkRequest.Builder(
            LoadProductsInListWorker::class.java
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        workManager
            .beginWith(requestProducts)
            .then(requestProductsInList)
            .enqueue()

        val responseFlow = workManager.getWorkInfoByIdLiveData(requestProductsInList.id).asFlow()
        return responseFlow
    }

    private suspend fun getProductsFromFlowWorkInfo(responseFlow: Flow<WorkInfo>): RepositoryResponse<List<ProductInListDTO>?, String> {
        responseFlow.collect {
            return@collect when(it.state) {
                WorkInfo.State.SUCCEEDED -> {
                    val products = it.outputData.getString(PRODUCTS_RESPONSE)
                    val productsInList = it.outputData.getString(PRODUCTS_IN_LIST_RESPONSE)
                }
                WorkInfo.State.BLOCKED -> {

                }
                WorkInfo.State.FAILED -> {

                }
                WorkInfo.State.CANCELLED -> {

                }


                else -> {}
            }
        }
    }

    override suspend fun addViewToProductInList(guid: String): RepositoryResponse<ProductInListDTO?, String> =
        sharedPreferencesApi.addViewToProductInListDTO(guid)

}

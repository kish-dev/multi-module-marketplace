package ru.ozon.route256.homework2.di


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.ozon.route256.homework2.data.repositoriesImpl.MockProductsRepositoryImpl
import ru.ozon.route256.homework2.domain.interactors.AddProductUseCase
import ru.ozon.route256.homework2.domain.interactors.ProductDetailUseCase
import ru.ozon.route256.homework2.domain.interactors.ProductListUseCase
import ru.ozon.route256.homework2.domain.interactors.ProductsInteractorImpl

// Подробнее можете почитать [тут](http://sergeyteplyakov.blogspot.com/2013/03/di-service-locator.html),
// [тут](https://habr.com/ru/post/465395/) и [тут](https://javatutor.net/articles/j2ee-pattern-service-locator).

class ServiceLocator {

    private val productsRepositoryImpl: ProductsInteractorImpl by lazy {
        ProductsInteractorImpl(
            MockProductsRepositoryImpl(),
            dispatcherIO
        )
    }

    val productListInteractor: ProductListUseCase by lazy {
        productsRepositoryImpl
    }

    val productDetailInteractor: ProductDetailUseCase by lazy {
        productsRepositoryImpl
    }

    val addProductInteractor: AddProductUseCase by lazy {
        productsRepositoryImpl
    }

    val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
}

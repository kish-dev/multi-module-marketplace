package ru.ozon.route256.workshop1.sources

fun ProductInListDTO.toVO() : ProductInListVO =
    ProductInListVO(guid, image, name, price, rating, isFavorite, isInCart)
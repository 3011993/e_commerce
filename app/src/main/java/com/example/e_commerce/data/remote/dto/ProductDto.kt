package com.example.e_commerce.data.remote.dto

import com.example.e_commerce.data.db.ProductEntity
import com.example.e_commerce.domain.model.ProductModel

data class ProductDto(
    val category: String?,
    val description: String?,
    val id: Int?,
    val image: String?,
    val price: String?,
    val title: String?,
)

fun ProductDto.toModel(): ProductModel =
    ProductModel(
        category ?: "",
        description ?: "",
        id ?: 0,
        image ?: "",
        price ?: "",
        title ?: ""
    )

fun List<ProductDto>.toDatabase(): Array<ProductEntity> {
    return map {
        ProductEntity(
            it.id ?: 0,
            it.category ?: "",
            it.description ?: "",
            it.image ?: "",
            price = it.price ?: "",
            title = it.title ?: ""
        )
    }.toTypedArray()
}
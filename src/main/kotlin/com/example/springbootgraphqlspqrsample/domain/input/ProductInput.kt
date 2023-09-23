package com.example.springbootgraphqlspqrsample.domain.input

import com.example.springbootgraphqlspqrsample.domain.model.Product
import java.math.BigDecimal

data class ProductInput(
    val name: String,
    val price: BigDecimal,
    val count: Int
)

fun ProductInput.toProduct() = Product(
    name = this.name,
    price = this.price,
    count = this.count
)

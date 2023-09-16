package com.example.springbootgraphqlspqrsample.domain.input

import com.example.springbootgraphqlspqrsample.domain.model.Product
import io.leangen.graphql.annotations.GraphQLNonNull
import java.math.BigDecimal

data class ProductInput(
    val name: @GraphQLNonNull String,
    val price: @GraphQLNonNull BigDecimal,
    val count: @GraphQLNonNull Int
)

fun ProductInput.toProduct() = Product(
    name = this.name,
    price = this.price,
    count = this.count
)

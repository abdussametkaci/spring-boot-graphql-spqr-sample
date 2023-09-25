package com.example.springbootgraphqlspqrsample.domain.model

import io.leangen.graphql.annotations.GraphQLNonNull
import java.math.BigDecimal
import java.util.UUID

data class Product(
    var id: @GraphQLNonNull UUID? = null,
    var name: String,
    var price: BigDecimal,
    var count: Int
)

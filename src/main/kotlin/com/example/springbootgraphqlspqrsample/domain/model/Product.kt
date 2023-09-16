package com.example.springbootgraphqlspqrsample.domain.model

import io.leangen.graphql.annotations.GraphQLNonNull
import java.math.BigDecimal
import java.util.UUID

data class Product(
    var id: @GraphQLNonNull UUID? = null,
    var name: @GraphQLNonNull String,
    var price: @GraphQLNonNull BigDecimal,
    var count: @GraphQLNonNull Int
)

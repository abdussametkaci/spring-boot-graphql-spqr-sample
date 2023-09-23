package com.example.springbootgraphqlspqrsample.domain.model

import java.math.BigDecimal
import java.util.UUID

data class Product(
    var id: UUID? = null,
    var name: String,
    var price: BigDecimal,
    var count: Int
)

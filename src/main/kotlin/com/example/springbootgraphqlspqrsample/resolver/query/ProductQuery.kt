package com.example.springbootgraphqlspqrsample.resolver.query

import com.example.springbootgraphqlspqrsample.domain.model.Product
import com.example.springbootgraphqlspqrsample.service.ProductService
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@GraphQLApi
class ProductQuery(private val productService: ProductService) {

    @GraphQLQuery
    fun products(): List<Product> = productService.getAll()

    @GraphQLQuery
    fun product(id: UUID): Product = productService.getById(id)
}

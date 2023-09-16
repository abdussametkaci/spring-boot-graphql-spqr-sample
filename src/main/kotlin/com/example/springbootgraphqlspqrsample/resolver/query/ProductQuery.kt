package com.example.springbootgraphqlspqrsample.resolver.query

import com.example.springbootgraphqlspqrsample.domain.model.Product
import com.example.springbootgraphqlspqrsample.service.ProductService
import io.leangen.graphql.annotations.GraphQLNonNull
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@GraphQLApi
class ProductQuery(private val productService: ProductService) {

    @GraphQLQuery
    fun products(): @GraphQLNonNull List<@GraphQLNonNull Product> = productService.getAll()

    @GraphQLQuery
    fun product(id: @GraphQLNonNull UUID): @GraphQLNonNull Product = productService.getById(id)
}

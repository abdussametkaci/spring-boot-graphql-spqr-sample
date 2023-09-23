package com.example.springbootgraphqlspqrsample.resolver.mutation

import com.example.springbootgraphqlspqrsample.domain.input.ProductInput
import com.example.springbootgraphqlspqrsample.domain.model.Product
import com.example.springbootgraphqlspqrsample.service.ProductService
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@GraphQLApi
class ProductMutation(private val productService: ProductService) {

    @GraphQLMutation
    fun createProduct(input: ProductInput): Product = productService.create(input)

    @GraphQLMutation
    fun updateProduct(id: UUID, input: ProductInput): Product = productService.update(id, input)

    @GraphQLMutation
    fun deleteProduct(id: UUID) = productService.deleteById(id)
}

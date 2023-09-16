package com.example.springbootgraphqlspqrsample.resolver.mutation

import com.example.springbootgraphqlspqrsample.domain.input.ProductInput
import com.example.springbootgraphqlspqrsample.domain.model.Product
import com.example.springbootgraphqlspqrsample.service.ProductService
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLNonNull
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@GraphQLApi
class ProductMutation(private val productService: ProductService) {

    @GraphQLMutation
    fun createProduct(input: @GraphQLNonNull ProductInput): @GraphQLNonNull Product = productService.create(input)

    @GraphQLMutation
    fun updateProduct(id: @GraphQLNonNull UUID, input: @GraphQLNonNull ProductInput): @GraphQLNonNull Product = productService.update(id, input)

    @GraphQLMutation
    fun deleteProduct(id: @GraphQLNonNull UUID) = productService.deleteById(id)
}

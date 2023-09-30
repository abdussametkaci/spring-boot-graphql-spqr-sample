package com.example.springbootgraphqlspqrsample.service

import com.example.springbootgraphqlspqrsample.domain.input.ProductInput
import com.example.springbootgraphqlspqrsample.domain.input.toProduct
import com.example.springbootgraphqlspqrsample.domain.model.Product
import com.example.springbootgraphqlspqrsample.repository.MockProductRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductService(private val productRepository: MockProductRepository) {

    fun getAll(): List<Product> = productRepository.findAll()

    fun getById(id: UUID): Product {
        return productRepository.findById(id) ?: throw Exception("Not Found id: $id")
    }

    fun create(input: ProductInput): Product {
        val product = input.toProduct()
        return productRepository.save(product)
    }

    fun update(id: UUID, input: ProductInput): Product {
        val product = productRepository.findById(id)
            ?.apply {
                name = input.name
                price = input.price
                count = input.count
                description = input.description
                specs = input.specs
            }
            ?: throw Exception("Not Found id: $id")

        return productRepository.save(product)
    }

    fun deleteById(id: UUID) {
        productRepository.deleteById(id)
    }
}

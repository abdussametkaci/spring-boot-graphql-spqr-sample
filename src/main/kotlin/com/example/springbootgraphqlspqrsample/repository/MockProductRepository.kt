package com.example.springbootgraphqlspqrsample.repository

import com.example.springbootgraphqlspqrsample.domain.model.Product
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.UUID

@Component
class MockProductRepository {

    fun findAll(): List<Product> = products

    fun findById(id: UUID): Product? {
        return products.find { it.id == id }
    }

    fun save(product: Product): Product {
        lateinit var savedProduct: Product
        if (product.id == null) {
            savedProduct = product.apply { id = UUID.randomUUID() }
            products.add(savedProduct)
        } else {
            savedProduct = findById(product.id!!)
                ?.apply {
                    name = product.name
                    price = product.price
                    count = product.count
                    description = product.description
                    specs = product.specs
                }
                ?: throw Exception("Not Found id: ${product.id}")
        }
        return savedProduct
    }

    fun deleteById(id: UUID) {
        products.removeIf { it.id == id }
    }

    private companion object {
        val products = mutableListOf(
            Product(
                id = UUID.randomUUID(),
                name = "Pencil",
                price = BigDecimal(5.5),
                count = 10
            ),
            Product(
                id = UUID.randomUUID(),
                name = "Notebook",
                price = BigDecimal(10.5),
                count = 25,
                description = "Nice Product"
            ),
            Product(
                id = UUID.randomUUID(),
                name = "Papers",
                price = BigDecimal(2.0),
                count = 1000,
                specs = listOf("White", "A4")
            ),
            Product(
                id = UUID.randomUUID(),
                name = "Tape Dispenser",
                price = BigDecimal(4.5),
                count = 1250,
                description = "Very Useful",
                specs = listOf("Small", "Red")
            ),
            Product(
                id = UUID.randomUUID(),
                name = "Label",
                price = BigDecimal(1.5),
                count = 875
            )
        )
    }
}

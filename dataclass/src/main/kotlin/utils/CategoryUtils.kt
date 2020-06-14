package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.Product

data class CategoryProducts(
    val category: Category,
    val productsInCategory: List<Product>,
    val productsInChildren: List<Product>
)

fun List<Category>.withProducts(products: List<Product>, categoryWithNoProducts: Boolean) = sequence {
    forEach { cat ->
        val (productsInCategory, productsInChildren) = products
            .filter { it.cat.first().let { mainCat -> cat.name == mainCat.name || cat.name in mainCat.allParents } }
            .partition { cat == it.cat.first() }
        if (categoryWithNoProducts || productsInCategory.isNotEmpty() || productsInChildren.isNotEmpty()) {
            yield(CategoryProducts(cat, productsInCategory, productsInChildren))
        }
    }
}

fun List<Category>.filterWithProducts(products: List<Product>): List<Category> = filter { cat ->
    products.firstOrNull { cat == it.cat.first() || cat.name in it.cat.first().allParents } != null
}
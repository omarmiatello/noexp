package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.ProductHome

data class CategoryProducts(
    val category: Category,
    val productsInCategory: List<ProductHome>,
    val productsInChildren: List<ProductHome>
)

fun List<Category>.withProducts(products: List<ProductHome>, categoryWithNoProducts: Boolean) = sequence {
    forEach { cat ->
        val (productsInCategory, productsInChildren) = products
            .filter { it.cat.first().let { mainCat -> cat.name == mainCat.name || cat.name in mainCat.allParents } }
            .partition { cat == it.cat.first() }
        if (categoryWithNoProducts || productsInCategory.isNotEmpty() || productsInChildren.isNotEmpty()) {
            yield(CategoryProducts(cat, productsInCategory, productsInChildren))
        }
    }
}

fun List<Category>.filterWithProducts(products: List<ProductHome>): List<Category> = filter { cat ->
    products.firstOrNull { cat == it.cat.first() || cat.name in it.cat.first().allParents } != null
}

fun String.extractCategories(categories: List<Category>, itemIfEmpty: Category? = null): List<Category> {
    fun String.cleanString() = replace("'", " ")
    fun findPosition(string: String, category: Category): Pair<Int, Category>? {
        var idx: Int
        idx = string.indexOf(" ${category.name.cleanString()} ", ignoreCase = true)
        if (idx >= 0) return idx to category
        category.alias.forEach {
            idx = string.indexOf(" ${it.cleanString()} ", ignoreCase = true)
            if (idx >= 0) return idx to category
        }
        return null
    }

    val sorted = categories
            .mapNotNull { category -> findPosition(" ${cleanString()} ", category) }
            .sortedByDescending { it.second.name }
            .sortedBy { it.first }.map { it.second }
    val parents = sorted.allParentsAsString
    return sorted.filter { it.name !in parents }
        .ifEmpty { if (itemIfEmpty != null) listOf(itemIfEmpty) else emptyList() }
}

val List<Category>.allParentsAsString
    get() = flatMap { it.allParents }.distinct()

fun List<Category>.allParents(
    categoriesMap: Map<String, Category>,
    default: (String) -> Category = { error("Missing category $it") }
) = flatMap { it.allParents }.distinct().map { categoriesMap[it] ?: default(it) }
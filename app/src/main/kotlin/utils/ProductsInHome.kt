package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.ProductHome

fun productsInHome(categories: List<Category>, products: List<ProductHome>) = buildString {
    categories
        .withProducts(products, categoryWithNoProducts = true)
        .forEach { categoryProducts ->
            val (category, productsInCategory, productsInChildren) = categoryProducts
            val tab = category.allParents.joinToString("") { "\t" }
            append("$tab${category.name} [${productsInCategory.size}/${productsInCategory.size + productsInChildren.size}]")
            val checkQuantity = categoryProducts.toCheckQuantity()
            if (checkQuantity.min != null) append(" | Quantity: Meno di min:${checkQuantity.min}")
            if (checkQuantity.desired != null) append(" | Quantity: Meno di desired:${checkQuantity.desired}")
            if (checkQuantity.max != null) append(" | Quantity: Più di max:${checkQuantity.max}")
            if (checkQuantity.productsDaysForConsume.isNotEmpty()) {
                append(" | Quantity: daysForConsume :")
                append(checkQuantity.productsDaysForConsume
                    .joinToString { "${it.product.name}[${it.productsLeft} in ${it.daysLeft} days < 1 product each ${it.minDaysForConsume} day] " })
            }
            if (productsInCategory.isNotEmpty()) {
                append(" (${productsInCategory.expiresFormatted()})")
                val tab2 = (category.allParents + "").joinToString("") { "\t" }
                productsInCategory.groupBy { it.name }.forEach { (_, prods) ->
                    appendLine()
                    append("$tab2 ${prods.size}x ${prods.first().name} (${prods.expiresFormatted()})")
                }
            }
            appendLine()
        }
}

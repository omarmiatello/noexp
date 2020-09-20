package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.ExpireDate
import com.github.omarmiatello.noexp.Product

data class CheckQuantity(
    val category: Category,
    val current: Int,
    val min: IssueInCategory?,
    val desired: IssueInCategory?,
    val max: IssueInCategory?,
    val productsDaysForConsume: List<ForProduct>
) {
    data class IssueInCategory(
        val current: Int,
        val expected: Int
    )

    data class ForProduct(
        val product: Product,
        val productsLeft: Int,
        val minDaysForConsume: Int,
        val daysLeft: Int
    )
}

fun CategoryProducts.toCheckQuantity(now: Long = System.currentTimeMillis()) =
    (productsInCategory + productsInChildren).let { products ->
        val min = category.quantity?.min ?: 0
        val max = category.quantity?.max ?: Int.MAX_VALUE
        val desired = category.quantity?.desired ?: if (max != Int.MAX_VALUE) (max - min) / 2 else min
        CheckQuantity(
            category = category,
            current = products.size,
            min = CheckQuantity.IssueInCategory(products.size, min).takeIf { it.current < it.expected },
            desired = CheckQuantity.IssueInCategory(products.size, desired).takeIf { it.current < it.expected },
            max = CheckQuantity.IssueInCategory(products.size, max).takeIf { it.current > it.expected },
            productsDaysForConsume = category.quantity?.minDaysForConsume?.let { minDaysForConsume ->
                products.mapNotNull { product ->
                    val expireDate = product.expireDate.valueOrNull ?: return@mapNotNull null
                    CheckQuantity.ForProduct(
                        product = product,
                        daysLeft = ((expireDate - now) / 24 / 60 / 60 / 1000).toInt(),
                        productsLeft = products.count {
                            val prodExpireDate = product.expireDate.valueOrNull ?: return@count false
                            prodExpireDate <= expireDate
                        },
                        minDaysForConsume = minDaysForConsume
                    ).takeIf { it.daysLeft.toDouble() / it.productsLeft < minDaysForConsume }
                }
            }.orEmpty()
        )
    }

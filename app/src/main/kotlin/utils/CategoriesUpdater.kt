package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.NoExpDB

fun List<Category>.updateQuantity(noExp: NoExpDB): List<Category> {
    val dbQuantity = noExp.category.orEmpty().mapValues { it.value.toQuantityOrNull() }
    return map { category ->
        val newQuantity = dbQuantity.getOrDefault(category.name, null)
        if (newQuantity != null) {
            category.copy(quantity = newQuantity)
        } else {
            category
        }
    }
}

package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.NoExpDB
import com.github.omarmiatello.noexp.extractCategories

fun NoExpDB.updateCategories(categories: List<Category>, forceUpdate: Boolean): NoExpDB {
    val categoryFood = categories.first()
    return copy(
        home = home?.mapValues {
            if (forceUpdate || it.value.cat.isNullOrEmpty()) {
                val name = it.value.name ?: error("Missing 'name' in ${it.value}")
                val cat = name.extractCategories(categories, itemIfEmpty = categoryFood)
                it.value.copy(cat = cat.toCatNames(), catParents = cat.toCatParentsNames())
            } else it.value

        },
        barcode = barcode?.mapValues {
            if (forceUpdate || it.value.cat.isNullOrEmpty()) {
                val name = it.value.name ?: error("Missing 'name' in ${it.value}")
                val cat = name.extractCategories(categories, itemIfEmpty = categoryFood)
                it.value.copy(cat = cat.toCatNames(), catParents = cat.toCatParentsNames())
            } else it.value
        },
        category = categories.map { it.name to it.toCategoryDao() }.toMap()
    )
}



fun List<Category>.toCatNames() = map { it.name }

fun List<Category>.toCatParentsNames() = flatMap { it.allParents }.distinct()
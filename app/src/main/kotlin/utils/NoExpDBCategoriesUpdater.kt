package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.NoExpDB

fun NoExpDB.updateDB(categories: List<Category>, forceUpdate: Boolean) = copy(
    home = home?.mapValues {
        if (forceUpdate || it.value.cat.isNullOrEmpty()) {
            val name = it.value.name ?: error("Missing 'name' in ${it.value}")
            val cat = name.extractCategories(categories, itemIfEmpty = categories.first())
            it.value.copy(
                cat = cat.map { it.name },
                catParents = cat.flatMap { it.allParents }.distinct()
            )
        } else it.value

    },
    barcode = barcode?.mapValues {
        if (forceUpdate || it.value.cat.isNullOrEmpty()) {
            val name = it.value.name ?: error("Missing 'name' in ${it.value}")
            val cat = name.extractCategories(categories, itemIfEmpty = categories.first())
            it.value.copy(
                cat = cat.map { it.name },
                catParents = cat.flatMap { it.allParents }.distinct()
            )
        } else it.value
    },
    category = categories.map { it.name to it.toCategoryDao() }.toMap()
).copy(
    expireDateByCategory = mapOfEstimateExpireDateByCategory(categories),
    expireDateByBarcode = mapOfEstimateExpireDateByBarcode()
)


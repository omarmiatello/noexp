package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.NoExpDB

fun updateDB(noExpDB: NoExpDB, categories: List<Category>, forceUpdate: Boolean): NoExpDB {
    var noExpDB = noExpDB.copy(
        home = noExpDB.home?.mapValues {
            if (forceUpdate || it.value.cat.isNullOrEmpty()) {
                val name = it.value.name ?: error("Missing 'name' in ${it.value}")
                val cat = name.extractCategories(categories, itemIfEmpty = categories.first())
                it.value.copy(
                    cat = cat.map { it.name },
                    catParents = cat.flatMap { it.allParents }.distinct()
                )
            } else it.value

        },
        category = categories.map { it.name to it.toCategoryDao() }.toMap(),
    )
    val mapOfEstimateExpireDaysByCategory = noExpDB.mapOfEstimateExpireDaysByCategory(categories)
    return noExpDB.copy(
        category = noExpDB.category?.mapValues { (key, value) ->
            val expireDays = mapOfEstimateExpireDaysByCategory[key]
            if (expireDays != null) {
                value.copy(expireDays = expireDays)
            } else value
        },
        expireDateByBarcode = noExpDB.mapOfEstimateExpireDateByBarcode(),
    )
}

package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.NoExpDB

fun updateDB(noExpDB: NoExpDB, categories: List<Category>, forceUpdate: Boolean): NoExpDB = noExpDB.copy(
    home = noExpDB.home?.mapValues { (_, productDao) ->
        if (forceUpdate || productDao.cat.isNullOrEmpty()) {
            val name = productDao.name ?: error("Missing 'name' in $productDao")
            val cat = name.extractCategories(categories, itemIfEmpty = categories.first())
            productDao.copy(
                cat = cat.map { it.name },
                catParents = cat.flatMap { it.allParents }.distinct()
            )
        } else productDao

    },
    category = categories.map { it.name to it.toCategoryDao() }.toMap(),
).let { db ->
    val mapOfEstimateExpireDaysByCategory = db.mapOfEstimateExpireDaysByCategory(categories)
    db.copy(
        category = db.category?.mapValues { (key, categoryDao) ->
            val expireDays = mapOfEstimateExpireDaysByCategory[key]
            if (expireDays != null) {
                categoryDao.copy(expireDays = expireDays)
            } else categoryDao
        },
        expireDateByBarcode = db.mapOfEstimateExpireDateByBarcode(),
    )
}

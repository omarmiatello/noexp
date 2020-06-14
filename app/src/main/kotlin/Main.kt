package com.github.omarmiatello.noexp

import com.github.omarmiatello.noexp.utils.*


fun main() {
    val isProduction = false

    val settings = cache("settings.json") { Settings() }

    val categories =
        cache("parsed-categories.json", forceUpdate = true) {
            NoExpCategoriesParser.parse(categoriesFile)
        }

    val noexp = cache("noexp.json", forceUpdate = isProduction) {
        ExternalData.getNoExp(settings.db_url, forceUpdate = true)
            .updateCategories(categories, forceUpdate = true)
            .updateEstimation(categories)
    }

    if (isProduction) {
        ExternalData.setNoExp(settings.db_url, noexp.toJson())
    }

    val products = noexp.home.orEmpty().values.map { it.toProduct(categories.associateBy { it.name }) }

    NoExpCategoriesParser.save(categoriesFile, categories, products)

    cacheText("productsInHome.txt", forceUpdate = true) {
        productsInHome(
            categories,
            products.sortedBy { it.expireDate })
    }
}



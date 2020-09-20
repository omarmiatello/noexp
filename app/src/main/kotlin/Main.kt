package com.github.omarmiatello.noexp

import com.github.omarmiatello.noexp.utils.*


fun main() {
    val isProduction = true

    val settings = cache("settings.json") { Settings() }

    var categories = cache("parsed-categories.json", forceUpdate = true) {
        NoExpCategoriesParser.parse(categoriesFile)
    }

    val noexp = cache("noexp.json", forceUpdate = isProduction) {
        val noExp = ExternalData.getNoExp(settings.db_url, forceUpdate = true)
        categories = categories.updateQuantity(noExp)
        updateDB(noExp, categories, forceUpdate = true)
    }

    if (isProduction) {
        ExternalData.setNoExp(settings.db_url, noexp.toJson())
    }

    val products = noexp.home.orEmpty().values.map { it.toProduct(categories.associateBy { it.name }) }

    NoExpCategoriesParser.save(categoriesFile, categories, products)

    cacheText("productsInHome.txt", forceUpdate = true) {
        productsInHome(
            categories,
            products.sortedBy { it.expireDate.valueOrNull ?: Long.MAX_VALUE })
    }
}

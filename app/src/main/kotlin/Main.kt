package com.github.omarmiatello.noexp


fun main() {
    val categories = cache("parsed-categories.json", forceUpdate = true) {
        NoExpCategoriesParser.parseAndRefactor(categoriesFile)
    }

    val noexp = cache("noexp.json", forceUpdate = false) {
        ExternalData.getNoExp("https://YOUR_URL", forceUpdate = true)
            .updateCategories(categories, forceUpdate = true)
    }

    // ExternalData.setNoExp("https://YOUR_URL", noexp.toJson())

    val products = noexp.home.orEmpty().values.map { it.toProduct(categories.associateBy { it.name }) }

    cacheText("productsInHome.txt", forceUpdate = true) {
        productsInHome(categories, products.sortedBy { it.expireDate })
    }
}
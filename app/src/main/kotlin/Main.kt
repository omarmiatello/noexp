package com.github.omarmiatello.noexp

import com.github.omarmiatello.noexp.utils.*

fun main() {
    println("🏁 Start!")
    val isProduction = false

    val settings = cache("settings.json") { Settings() }
    println("🔧 Setting: $settings")

    var categories = cache("parsed-categories.json", forceUpdate = true) {
        NoExpCategoriesParser.parse(categoriesFile)
    }

    val noexp = cache("noexp.json", forceUpdate = isProduction) {
        val noExp = ExternalData.getNoExp(settings.db_url, forceUpdate = true)
        categories = categories.updateQuantity(noExp)
        updateDB(noExp, categories, forceUpdate = true)
    }

    cacheText("sample.kt", forceUpdate = false) {
        """import com.github.omarmiatello.noexp.NoExpDB
import com.github.omarmiatello.noexp.NoExpDBModel.*

object SampleDb {
    val noExp: NoExpDB = ${noexp.toConstructorString(maxSample = 5)}
}"""
    }

    if (isProduction) {
        println("🌎 Prepare upload")
        ExternalData.setNoExp(settings.db_url, noexp.toJson())
        println("🌎 Upload complete")
    }

    val products = noexp.home.orEmpty().values.map { it.toProductQr(categories.associateBy { it.name }) }

    NoExpCategoriesParser.save(categoriesFile, categories, products)

    cacheText("productsInHome.txt", forceUpdate = true) {
        productsInHome(
            categories,
            products.sortedBy { it.expireDate.valueIfRealOrEstimate ?: Long.MAX_VALUE })
    }
    println("🎉 Finish!")
}

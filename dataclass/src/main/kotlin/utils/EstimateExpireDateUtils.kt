package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.NoExpDB
import com.github.omarmiatello.noexp.NoExpDBModel
import kotlin.math.roundToInt
import kotlin.math.roundToLong

fun mapOfEstimateExpireDateByCategory(
    categories: List<Category>,
    barcode: Map<String, NoExpDBModel.BarcodeDao>,
    productList: List<NoExpDBModel.ProductDao>,
    archiveList: List<NoExpDBModel.ArchivedDao>
): Map<String, Long> {
    val productCategoryDays = productList
        .mapNotNull {
            val category = it.cat!!.first()
            val expireInDays = it.expireInDays
            if (expireInDays < 0) return@mapNotNull null
            category to expireInDays
        }
    val archiveCategoryDays = archiveList
        .mapNotNull {
            checkNotNull(it.barcode) { "Missing barcode in $it" }
            val barcodeDao = barcode[it.barcode] ?: return@mapNotNull null
            val category = barcodeDao.cat?.first() ?: barcodeDao.name?.extractCategories(categories, categories.first())!!.first().name
            val expireInDays = it.expireInDays
            if (expireInDays < 0) return@mapNotNull null
            category to expireInDays
        }
    val categoryDays = (productCategoryDays + archiveCategoryDays).groupBy({ it.first }) { it.second }
    return categoryDays
        .flatMap { (k, v) ->
            categories.first { it.name == k }.allParents.map { it to v } + (k to v)
        }
        .groupBy({ it.first }) { it.second }
        .mapValues {
            val expireDates = it.value.flatten()
            val excluded = (expireDates.size * 0.1).roundToInt()
            val expireDatesFiltered =
                if (expireDates.size > 5) expireDates.drop(excluded).dropLast(excluded) else expireDates
            expireDatesFiltered.average().roundToLong()
        }
}

fun mapOfEstimateExpireDateByBarcode(
    productList: List<NoExpDBModel.ProductDao>,
    archiveList: List<NoExpDBModel.ArchivedDao>
): Map<String, Long> {
    val productCategoryDays = productList
        .mapNotNull {
            if (it.barcode == null) return@mapNotNull null
            val expireInDays = it.expireInDays
            if (expireInDays < 0) return@mapNotNull null
            it.barcode to expireInDays
        }
    val archiveCategoryDays = archiveList
        .mapNotNull {
            checkNotNull(it.barcode) { "Missing barcode in $it" }
            val expireInDays = it.expireInDays
            if (expireInDays < 0) return@mapNotNull null
            it.barcode to expireInDays
        }
    return (productCategoryDays + archiveCategoryDays)
        .groupBy({ it.first }) { it.second }
        .mapValues { it.value.average().roundToLong() }
}

fun NoExpDB.mapOfEstimateExpireDateByCategory(categories: List<Category> = category!!.values.map { it.toCategory() }) =
    mapOfEstimateExpireDateByCategory(
        categories,
        barcode!!,
        home!!.values.toList(),
        archive!!.values.flatMap { it.values }
    )

fun NoExpDB.mapOfEstimateExpireDateByBarcode() = mapOfEstimateExpireDateByBarcode(
    productList = home!!.values.toList(),
    archiveList = archive!!.values.flatMap { it.values }
)

private fun expireInDays(expireDate: Long, insertDate: Long) = (expireDate - insertDate) / 1000 / 60 / 60 / 24

private val NoExpDBModel.ArchivedDao.expireInDays get() = expireInDays(expireDate!!, insertDate!!)

private val NoExpDBModel.ProductDao.expireInDays get() = expireInDays(expireDate!!, insertDate!!)

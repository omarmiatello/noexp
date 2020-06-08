package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.NoExpDB
import com.github.omarmiatello.noexp.NoExpDBModel
import kotlin.math.roundToInt
import kotlin.math.roundToLong

fun NoExpDB.updateEstimation(
    categories: List<Category>
) = copy(
    expireDateByCategory = cache("estimateCategoryExpireDate.json", forceUpdate = true) {
        estimateCategoryExpireDate(
            categories,
            barcode!!,
            home!!.values.toList(),
            archive!!.values.flatMap { it.values })
    },
    expireDateByBarcode = cache("estimateBarcodeExpireDate.json", forceUpdate = true) {
        estimateBarcodeExpireDate(
            barcode!!,
            home!!.values.toList(),
            archive!!.values.flatMap { it.values })
    }
)

fun estimateCategoryExpireDate(
    categories: List<Category>,
    barcode: Map<String, NoExpDBModel.BarcodeDao>,
    productList: List<NoExpDBModel.ProductDao>,
    archiveList: List<NoExpDBModel.ArchivedDao>
): Map<String, Long> {
    return categoryDays(barcode, productList, archiveList).flatMap { (k, v) ->
        categories.first { it.name == k }.allParents.map { it to v } + (k to v)
    }
        .groupBy({ it.first }) { it.second }
        .mapValues {
            val expireDates = it.value.flatten()
            val excluded = (expireDates.size * 0.1).roundToInt()
            val expireDatesFiltered = if (expireDates.size > 5) expireDates.drop(excluded).dropLast(excluded) else expireDates
            expireDatesFiltered.average().roundToLong()
        }
}

fun estimateBarcodeExpireDate(
    barcode: Map<String, NoExpDBModel.BarcodeDao>,
    productList: List<NoExpDBModel.ProductDao>,
    archiveList: List<NoExpDBModel.ArchivedDao>
): Map<String, Long> {
    val productCategoryDays = productList
        .mapNotNull {
            if (it.barcode == null) return@mapNotNull null
            (it.barcode!! to expireInDays(it.expireDate!!, it.insertDate!!))
                .takeIf { it.second > 0 }
        }
    val archiveCategoryDays = archiveList
        .mapNotNull {
            barcode[it.barcode ?: error("Missing barcode in $it")]?.let { barcodeDao ->
                it.barcode!! to expireInDays(it.expireDate!!, it.insertDate!!)
            }?.takeIf { it.second > 0 }
        }
    return (productCategoryDays + archiveCategoryDays)
        .groupBy({ it.first }) { it.second }
        .mapValues { it.value.average().roundToLong() }
}

private fun categoryDays(
    barcode: Map<String, NoExpDBModel.BarcodeDao>,
    productList: List<NoExpDBModel.ProductDao>,
    archiveList: List<NoExpDBModel.ArchivedDao>
): Map<String, List<Long>> {
    val productCategoryDays = productList
        .mapNotNull {
            (it.cat!!.first() to expireInDays(it.expireDate!!, it.insertDate!!))
                .takeIf { it.second > 0 }
        }
    val archiveCategoryDays = archiveList
        .mapNotNull {
            barcode[it.barcode ?: error("Missing barcode in $it")]?.let { barcodeDao ->
                barcodeDao.cat!!.first() to expireInDays(it.expireDate!!, it.insertDate!!)
            }?.takeIf { it.second > 0 }
        }
    return (productCategoryDays + archiveCategoryDays).groupBy({ it.first }) { it.second }
}

private fun expireInDays(expireDate: Long, insertDate: Long) =
    (expireDate - insertDate) / 1000 / 60 / 60 / 24
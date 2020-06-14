package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Category
import com.github.omarmiatello.noexp.NoExpDB

fun NoExpDB.updateEstimation(categories: List<Category>) = copy(
    expireDateByCategory = cache("estimateCategoryExpireDate.json", forceUpdate = true) {
        mapOfEstimateExpireDateByCategory(categories)
    },
    expireDateByBarcode = cache("estimateBarcodeExpireDate.json", forceUpdate = true) {
        mapOfEstimateExpireDateByBarcode()
    }
)

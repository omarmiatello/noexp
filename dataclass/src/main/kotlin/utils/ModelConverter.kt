package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.*


// DB -> App models

fun NoExpDB.toProductsAndCategories(): Pair<List<Product>, List<Category>> {
    val categoriesMap = category.orEmpty().mapValues { it.value.toCategory() }
    return home.orEmpty().values.map { it.toProduct(categoriesMap) } to categoriesMap.values.toList()
}

fun NoExpDBModel.ProductDao.toProduct(categoriesMap: Map<String, Category>) = Product(
    name = name ?: error("Missing 'name' in $this"),
    description = description,
    pictureUrl = pictureUrl,
    barcode = barcode,
    qr = qr ?: error("Missing 'qr' in $this"),
    insertDate = insertDate ?: error("Missing 'insertDate' in $this"),
    expireDate = expireDate ?: error("Missing 'expireDate' in $this"),
    quantity = toQuantityOrNull(),
    cat = cat.orEmpty().map { categoriesMap.getValue(it) },
    catParents = catParents.orEmpty().map { categoriesMap.getValue(it) }
)

fun NoExpDBModel.ProductDao.toQuantityOrNull() =
    if (min != null || desired != null || max != null || maxPerWeek != null || maxPerYear != null) {
        Quantity(
            min = min,
            desired = desired,
            max = max,
            maxPerWeek = maxPerWeek,
            maxPerYear = maxPerYear
        )
    } else null

fun NoExpDBModel.CategoryDao.toCategory() = Category(
    name = name ?: error("Missing 'name' in $this"),
    alias = alias.orEmpty(),
    directParent = directParent,
    allParents = allParents.orEmpty(),
    directChildren = directChildren.orEmpty(),
    quantity = toQuantityOrNull()
)

fun NoExpDBModel.CategoryDao.toQuantityOrNull() =
    if (min != null || desired != null || max != null || maxPerWeek != null || maxPerYear != null) {
        Quantity(
            min = min,
            desired = desired,
            max = max,
            maxPerWeek = maxPerWeek,
            maxPerYear = maxPerYear
        )
    } else null

// App models -> DB

fun Product.toProductDao() = NoExpDBModel.ProductDao(
    name = name,
    description = description,
    pictureUrl = pictureUrl,
    barcode = barcode,
    qr = qr,
    insertDate = insertDate,
    expireDate = expireDate,
    min = quantity?.min,
    desired = quantity?.desired,
    max = quantity?.max,
    maxPerWeek = quantity?.maxPerWeek,
    maxPerYear = quantity?.maxPerYear,
    cat = cat.map { it.name }.takeIf { it.isNotEmpty() },
    catParents = catParents.map { it.name }.takeIf { it.isNotEmpty() }
)

fun Product.toBarcodeDao() = NoExpDBModel.BarcodeDao(
    name = name,
    description = description,
    pictureUrl = pictureUrl,
    barcode = barcode,
    max = quantity?.max,
    min = quantity?.min,
    desired = quantity?.desired,
    maxPerWeek = quantity?.maxPerWeek,
    maxPerYear = quantity?.maxPerYear,
    cat = cat.map { it.name }.takeIf { it.isNotEmpty() },
    catParents = catParents.map { it.name }.takeIf { it.isNotEmpty() }
)

fun Product.toArchivedDao(archiveDate: Long = System.currentTimeMillis()) = NoExpDBModel.ArchivedDao(
    barcode = barcode ?: error("Missing 'barcode' in $this"),
    qr = qr,
    insertDate = insertDate,
    expireDate = expireDate,
    archiveDate = archiveDate
)

fun Category.toCategoryDao() = NoExpDBModel.CategoryDao(
    name = name,
    alias = alias.takeIf { it.isNotEmpty() },
    directParent = directParent,
    allParents = allParents.takeIf { it.isNotEmpty() },
    directChildren = directChildren.takeIf { it.isNotEmpty() },
    min = quantity?.min,
    desired = quantity?.desired,
    max = quantity?.max,
    maxPerWeek = quantity?.maxPerWeek,
    maxPerYear = quantity?.maxPerYear
)

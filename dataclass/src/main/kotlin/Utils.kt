package com.github.omarmiatello.noexp

import kotlin.math.abs

fun expireInDays(now: Long, expireDate: Long) = (expireDate - now) / (1000 * 60 * 60 * 24)

fun expireFormatted(now: Long, expireDate: Long): String {
    val h = ((expireDate - now) / (1000 * 60 * 60)).also { if (abs(it) < 36) return "${it}h" }
    val d = (h / 24).also { if (abs(it) < 45) return "${it}d" }
    val m = (d / 30).also { if (abs(it) < 13) return "${it}M" }
    return "${m / 12}y"
}

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
    min = min,
    desired = desired,
    max = max,
    maxPerWeek = maxPerWeek,
    maxPerYear = maxPerYear,
    cat = cat.orEmpty().map { categoriesMap.getValue(it) },
    catParents = catParents.orEmpty().map { categoriesMap.getValue(it) }
)

fun NoExpDBModel.CategoryDao.toCategory() = Category(
    name = name ?: error("Missing 'name' in $this"),
    alias = alias.orEmpty(),
    directParent = directParent,
    allParents = allParents.orEmpty(),
    directChildren = directChildren.orEmpty(),
    min = min,
    desired = desired,
    max = max,
    maxPerWeek = maxPerWeek,
    maxPerYear = maxPerYear
)


// App models -> DB

fun Product.toBarcodeDao() = NoExpDBModel.BarcodeDao(
    name = name,
    description = description,
    pictureUrl = pictureUrl,
    barcode = barcode,
    max = max,
    min = min,
    desired = desired,
    maxPerWeek = maxPerWeek,
    maxPerYear = maxPerYear,
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
    min = min,
    desired = desired,
    max = max,
    maxPerWeek = maxPerWeek,
    maxPerYear = maxPerYear
)

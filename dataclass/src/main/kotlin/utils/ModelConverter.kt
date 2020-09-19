package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.*

object ExpireDateKeys {
    const val REAL = "R"
    const val ESTIMATED = "E"
    const val NOEXP = "N"
    const val UNKNOWN = "U"
}

fun expireDateFrom(type: String?, expireDate: Long?, category: Category?, insertDate: Long) = when (type) {
    null,
    ExpireDateKeys.REAL -> ExpireDate.Real(expireDate ?: error("Missing expireDate for ExpireDate.Real"))
    ExpireDateKeys.UNKNOWN,
    ExpireDateKeys.ESTIMATED -> category?.estimateExpireDate(insertDate)?.let { ExpireDate.Estimated(it) }
        ?: ExpireDate.Unknown
    ExpireDateKeys.NOEXP -> ExpireDate.NoExp
    else -> error("Unknown ExpireDate type $type, with: $expireDate, $category, $insertDate")
}

fun ExpireDate.toExpireDateDao() = when (this) {
    is ExpireDate.Real -> ExpireDateKeys.REAL to value
    is ExpireDate.Estimated -> ExpireDateKeys.ESTIMATED to value
    ExpireDate.NoExp -> ExpireDateKeys.NOEXP to null
    ExpireDate.Unknown -> ExpireDateKeys.UNKNOWN to null
}


// DB -> App models

fun NoExpDB.toProductsAndCategories(): Pair<List<Product>, List<Category>> {
    val categoriesMap = category.orEmpty().mapValues { it.value.toCategory() }
    return home.orEmpty().values.map { it.toProduct(categoriesMap) } to categoriesMap.values.toList()
}

fun NoExpDBModel.ProductDao.toProduct(categoriesMap: Map<String, Category>): Product {
    val categories = cat.orEmpty().map { categoriesMap.getValue(it) }
    return Product(
        name = name ?: error("Missing 'name' in $this"),
        pictureUrl = pictureUrl,
        barcode = barcode,
        qr = qr ?: error("Missing 'qr' in $this"),
        insertDate = insertDate ?: error("Missing 'insertDate' in $this"),
        expireDate = expireDateFrom(expireDateType, expireDate, categories.firstOrNull(), insertDate),
        lastCheckDate = lastCheckDate ?: insertDate,
        cat = categories,
        catParents = catParents.orEmpty().map { categoriesMap.getValue(it) },
        position = position,
    )
}

fun NoExpDBModel.ProductCartDao.toProductCart(categoriesMap: Map<String, Category>): ProductCart {
    val categories = cat.orEmpty().map { categoriesMap.getValue(it) }
    return ProductCart(
        name = name ?: error("Missing 'name' in $this"),
        pictureUrl = pictureUrl,
        barcode = barcode,
        insertDate = insertDate ?: error("Missing 'insertDate' in $this"),
        expireDate = expireDateFrom(expireDateType, expireDate, categories.firstOrNull(), insertDate),
        cat = categories,
        catParents = catParents.orEmpty().map { categoriesMap.getValue(it) },
    )
}

fun NoExpDBModel.CategoryDao.toCategory() = Category(
    name = name ?: error("Missing 'name' in $this"),
    alias = alias.orEmpty(),
    directParent = directParent,
    allParents = allParents.orEmpty(),
    directChildren = directChildren.orEmpty(),
    quantity = toQuantityOrNull(),
    expireDays = expireDays,
)

fun NoExpDBModel.CategoryDao.toQuantityOrNull() =
    if (min != null || desired != null || max != null || maxPerWeek != null || maxPerYear != null) {
        Quantity(
            min = min,
            desired = desired,
            max = max,
            maxPerWeek = maxPerWeek,
            maxPerYear = maxPerYear,
        )
    } else null

// App models -> DB

fun Product.toProductDao(): NoExpDBModel.ProductDao {
    val (expireDateType, expireDate) = expireDate.toExpireDateDao()
    return NoExpDBModel.ProductDao(
        name = name,
        pictureUrl = pictureUrl,
        barcode = barcode,
        qr = qr,
        insertDate = insertDate,
        expireDate = expireDate,
        expireDateType = expireDateType,
        lastCheckDate = lastCheckDate,
        cat = cat.map { it.name }.takeIf { it.isNotEmpty() },
        catParents = catParents.map { it.name }.takeIf { it.isNotEmpty() },
        position = position,
    )
}

fun Product.toBarcodeDao() = NoExpDBModel.BarcodeDao(
    name = name,
    pictureUrl = pictureUrl,
    barcode = barcode,
)

fun Product.toArchivedDao(archiveDate: Long = System.currentTimeMillis()): NoExpDBModel.ArchivedDao {
    val (expireDateType, expireDate) = expireDate.toExpireDateDao()
    return NoExpDBModel.ArchivedDao(
        barcode = barcode ?: error("Missing 'barcode' in $this"),
        qr = qr,
        insertDate = insertDate,
        expireDate = expireDate,
        expireDateType = expireDateType,
        archiveDate = archiveDate,
    )
}

fun ProductCart.toProductCartDao(): NoExpDBModel.ProductCartDao {
    val (expireDateType, expireDate) = expireDate.toExpireDateDao()
    return NoExpDBModel.ProductCartDao(
        name = name,
        pictureUrl = pictureUrl,
        barcode = barcode,
        insertDate = insertDate,
        expireDate = expireDate,
        expireDateType = expireDateType,
        cat = cat.map { it.name }.takeIf { it.isNotEmpty() },
        catParents = catParents.map { it.name }.takeIf { it.isNotEmpty() },
    )
}

fun ProductCart.toProduct(qr: String, lastCheckDate: Long = System.currentTimeMillis()) = Product(
    name = name,
    pictureUrl = pictureUrl,
    barcode = barcode,
    qr = qr,
    insertDate = insertDate,
    expireDate = expireDate,
    lastCheckDate = lastCheckDate,
    cat = cat,
    catParents = catParents,
    position = null,
)

fun ProductCart.toBarcodeDao() = NoExpDBModel.BarcodeDao(
    name = name,
    pictureUrl = pictureUrl,
    barcode = barcode,
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
    maxPerYear = quantity?.maxPerYear,
    expireDays = expireDays,
)

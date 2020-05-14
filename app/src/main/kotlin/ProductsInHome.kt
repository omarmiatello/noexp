package com.github.omarmiatello.noexp

fun productsByCategories(categories: List<Category>, products: List<Product>) = buildString {
    val categoryNameToFirstProduct = categories.mapNotNull { cat ->
        val firstProduct = products.firstOrNull { cat == it.cat.first() || cat.name in it.cat.first().allParents }
        firstProduct?.let { cat.name to firstProduct }
    }.toMap()
    val maxExpire = products.map { it.expireDate }.max()
    val visibleCategories = cache("visible-categories.json", forceUpdate = true) {
        categories.filter { cat ->
            products.firstOrNull { cat == it.cat.first() || cat.name in it.cat.first().allParents } != null
        }
    }

    visibleCategories
        .sortedBy { cat ->
            fun String.expireDate(): String = (categoryNameToFirstProduct[this]?.expireDate
                ?: maxExpire).toString().padStart(20, '0')
            (cat.allParents + cat.name).joinToString("") { it.expireDate() + it }
        }
        .forEach { cat ->
            val (prodsCat, prodsParent) = products.filter { cat in it.cat || cat in it.catParents }
                .partition { cat == it.cat.first() }
            if (prodsCat.isNotEmpty() || prodsParent.isNotEmpty()) {
                val tab = cat.allParents.joinToString("") { "\t" }
                append("$tab${cat.name} [${prodsCat.size}/${prodsCat.size + prodsParent.size}]")
                if (prodsCat.isNotEmpty()) {
                    append(" (${prodsCat.expiresFormatted()})")
                    val tab2 = (cat.allParents + "").joinToString("") { "\t" }
                    prodsCat.groupBy { it.name }.forEach { (_, prods) ->
                        appendln()
                        append("$tab2 ${prods.size}x ${prods.first().name} (${prods.expiresFormatted()})")
                    }
                }
                appendln()
            }
        }
}

private fun List<Product>.expiresFormatted() = map { it.expireFormatted() }
    .groupBy { it }
    .toList()
    .joinToString { if (it.second.size == 1) it.first else "${it.second.size}x ${it.first}" }

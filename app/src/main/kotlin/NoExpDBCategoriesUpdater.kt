package com.github.omarmiatello.noexp

fun NoExpDB.updateCategories(categories: List<Category>, forceUpdate: Boolean): NoExpDB {
    fun List<Category>.names() = map { it.name }
    fun List<Category>.parentNames() = flatMap { it.allParents }.distinct()
    val categoryFood = categories.first()
    return copy(
        home = home?.mapValues {
            if (forceUpdate || it.value.cat.isNullOrEmpty()) {
                val name = it.value.name ?: error("Missing 'name' in ${it.value}")
                val cat = name.parseCategories(categories, itemIfEmpty = categoryFood)
                it.value.copy(cat = cat.names(), catParents = cat.parentNames())
            } else it.value

        },
        barcode = barcode?.mapValues {
            if (forceUpdate || it.value.cat.isNullOrEmpty()) {
                val name = it.value.name ?: error("Missing 'name' in ${it.value}")
                val cat = name.parseCategories(categories, itemIfEmpty = categoryFood)
                it.value.copy(cat = cat.names(), catParents = cat.parentNames())
            } else it.value
        },
        category = categories.map { it.name to it.toCategoryDao() }.toMap()
    )
}

private fun String.parseCategories(categories: List<Category>, itemIfEmpty: Category? = null): List<Category> {
    fun findPosition(string: String, category: Category): Pair<Int, Category>? {
        var idx: Int
        idx = string.indexOf(" ${category.name} ", ignoreCase = true)
        if (idx >= 0) return idx to category
        category.alias.forEach {
            idx = string.indexOf(" $it ", ignoreCase = true)
            if (idx >= 0) return idx to category
        }
        return null
    }

    val sorted = categories.mapNotNull { category -> findPosition(" $this ", category) }.sortedByDescending { it.second.name }.sortedBy { it.first }.map { it.second }
    val parents = sorted.flatMap { it.allParents }
    return sorted.filter { it.name !in parents }
        .ifEmpty { if (itemIfEmpty != null) listOf(itemIfEmpty) else emptyList() }
}
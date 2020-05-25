package com.github.omarmiatello.noexp

import com.github.omarmiatello.noexp.utils.toCategoryDao

fun NoExpDB.updateCategories(categories: List<Category>, forceUpdate: Boolean): NoExpDB {
    val categoryFood = categories.first()
    return copy(
        home = home?.mapValues {
            if (forceUpdate || it.value.cat.isNullOrEmpty()) {
                val name = it.value.name ?: error("Missing 'name' in ${it.value}")
                val cat = name.extractCategories(categories, itemIfEmpty = categoryFood)
                it.value.copy(cat = cat.toCatNames(), catParents = cat.toCatParentsNames())
            } else it.value

        },
        barcode = barcode?.mapValues {
            if (forceUpdate || it.value.cat.isNullOrEmpty()) {
                val name = it.value.name ?: error("Missing 'name' in ${it.value}")
                val cat = name.extractCategories(categories, itemIfEmpty = categoryFood)
                it.value.copy(cat = cat.toCatNames(), catParents = cat.toCatParentsNames())
            } else it.value
        },
        category = categories.map { it.name to it.toCategoryDao() }.toMap()
    )
}

fun String.extractCategories(categories: List<Category>, itemIfEmpty: Category? = null): List<Category> {
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

fun List<Category>.toCatNames() = map { it.name }

fun List<Category>.toCatParentsNames() = flatMap { it.allParents }.distinct()
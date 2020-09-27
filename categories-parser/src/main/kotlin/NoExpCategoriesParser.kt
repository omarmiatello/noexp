package com.github.omarmiatello.noexp

import java.io.File

object NoExpCategoriesParser {
    fun parse(file: File): List<Category> {
        var currentParents = listOf("")
        var tab = 0
        val childParents = file.readLines().drop(1).map { it.toCategoryLine() }.map { line ->
            val parents = when (val diff = line.indent - tab) {
                in -10..0 -> currentParents.dropLast(1 - diff)
                1 -> currentParents
                else -> error("The number of tabs should beetween -10/+1: $line $diff")
            }
            currentParents = parents + line.name
            tab = line.indent
            line to parents
        }
        val children = childParents.groupBy({ it.second.lastOrNull() }, { it.first.name })
        val categories = childParents.map { (line, parents) ->
            Category(
                name = line.name,
                alias = line.alias,
                directParent = parents.lastOrNull(),
                allParents = parents,
                directChildren = children[line.name].orEmpty(),
                quantity = line.run {
                    if (min != null || desired != null || max != null || maxPerWeek != null || maxPerYear != null) {
                        Quantity(
                            min = min,
                            desired = desired,
                            max = max,
                            maxPerWeek = maxPerWeek,
                            maxPerYear = maxPerYear
                        )
                    } else null
                }
            )
        }.sorted()
        val categoryDuplicated =
            categories.flatMap { it.alias + it.name }.groupBy { it }.filterValues { it.size > 1 }.keys
        if (categoryDuplicated.isNotEmpty()) error("There are some categories duplicated: ${categoryDuplicated.joinToString()}")
        return categories
    }

    fun save(file: File, categories: List<Category>, products: List<ProductHome>) =
        file.writeText(categories.toFormattedString(products))

    private fun String.toCategoryLine(): CategoryLine {
        val tabsCount = takeWhile { !it.isLetter() }.let { nonLetter ->
            val space = nonLetter.count { it == ' ' }
            nonLetter.count() - space + (space / 4)
        }
        if (tabsCount > 10) error("Are you sure? Seems too depth ($tabsCount tabs)")
        val (name, current, quantity, week, year, alias) = split("|").map { it.trim() }
        val (min, desired, max) = quantity.split(" ").map { it.toInt().takeIf { it != 0 } }
        return CategoryLine(
            indent = tabsCount,
            name = name,
            alias = alias.split(",").map { it.trim() } - "",
            min = min,
            desired = desired,
            max = max,
            maxPerWeek = week.toIntOrNull(),
            maxPerYear = year.toIntOrNull(),
            current = current.toIntOrNull() ?: 0
        )
    }

    private fun List<Category>.toFormattedString(products: List<ProductHome>) = buildString {
        append("--==[ Categories ]==--".padEnd(40))
        appendLine(" | current | quantity | week | year | alias")
        this@toFormattedString.forEach { category ->
            val tabulation = category.allParents.joinToString("") { "\t" }
            val tabulationSize = category.allParents.size * 4
            val productsCount = products.count {
                it.cat.first().let { cat -> category.name == cat.name || category.name in cat.allParents }
            }
            val min = category.quantity?.min ?: 0
            val desired = category.quantity?.desired ?: 0
            val max = category.quantity?.max ?: 0
            val maxPerWeek = if (category.quantity?.maxPerWeek ?: 0 > 0) category.quantity?.maxPerWeek else ""
            val maxPerYear = if (category.quantity?.maxPerYear ?: 0 > 0) category.quantity?.maxPerYear else ""
            append(tabulation)
            append(category.name.padEnd(40 - tabulationSize))
            append(" | $productsCount".padEnd(10))
            append(" | $min $desired $max".padEnd(11))
            append(" | $maxPerWeek".padEnd(7))
            append(" | $maxPerYear".padEnd(7))
            appendLine(" | ${category.alias.joinToString()}")
        }
    }


    private operator fun <E> List<E>.component6() = get(5)

    private class CategoryLine(
        val indent: Int,
        val name: String,
        val alias: List<String>,
        val min: Int?,
        val desired: Int?,
        val max: Int?,
        val maxPerWeek: Int?,
        val maxPerYear: Int?,
        val current: Int
    )
}

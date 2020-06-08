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
                min = line.min,
                desired = line.desired,
                max = line.max,
                maxPerWeek = line.maxPerWeek,
                maxPerYear = line.maxPerYear
            )
        }.sorted()
        val categoryDuplicated = categories.flatMap { it.alias + it.name }.groupBy { it }.filterValues { it.size > 1 }.keys
        if (categoryDuplicated.isNotEmpty()) error("There are some categories duplicated: ${categoryDuplicated.joinToString()}")
        return categories
    }

    fun save(file: File, categories: List<Category>) = file.writeText(categories.toFormattedString())

    fun parseAndRefactor(file: File) = parse(file).also { save(file, it) }

    private fun String.toCategoryLine(): CategoryLine {
        val tabsCount = takeWhile { !it.isLetter() }.let { nonLetter ->
            val space = nonLetter.count { it == ' ' }
            nonLetter.count() - space + (space / 4)
        }
        if (tabsCount > 10) error("Are you sure? Seems too depth ($tabsCount tabs)")
        val (name, quantity, week, year, alias) = split("|").map { it.trim() }
        val (min, desired, max) = quantity.split(" ").map { it.toInt().takeIf { it != 0 } }
        return CategoryLine(
            indent = tabsCount,
            name = name,
            alias = alias.split(",").map { it.trim() } - "",
            min = min,
            desired = desired,
            max = max,
            maxPerWeek = week.toIntOrNull(),
            maxPerYear = year.toIntOrNull()
        )
    }

    private fun List<Category>.toFormattedString() = "${"--==[ Categories ]==--".padEnd(40)} | quantity | week | year | alias\n" + joinToString("\n") {
        buildString {
            append(it.allParents.joinToString("") { "\t" })
            append(it.name.padEnd(40 - (it.allParents.size * 4)))
            append(" | ${it.min ?: 0} ${it.desired ?: 0} ${it.max ?: 0}".padEnd(11))
            append(" | ${if (it.maxPerWeek ?: 0 > 0) it.maxPerWeek else ""}".padEnd(7))
            append(" | ${if (it.maxPerYear ?: 0 > 0) it.maxPerYear else ""}".padEnd(7))
            append(" | ${it.alias.joinToString()}")
        }
    }

    private class CategoryLine(val indent: Int, val name: String, val alias: List<String>, val min: Int?, val desired: Int?, val max: Int?, val maxPerWeek: Int?, val maxPerYear: Int?)
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
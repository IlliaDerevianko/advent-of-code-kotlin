import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun distance(p1: Pair<Int, Int>, p2: Pair<Int, Int>) = abs(p1.first - p2.first) + abs(p1.second - p2.second)
    fun part1(input: List<String>, expansionFactor: Int = 1): Long {
        val rowsToExpand: MutableList<Int> = mutableListOf()
        for(i in input.indices) {
            if (input[i].all { it == '.' }) {
                rowsToExpand.add(i)
            }
        }
        val columnsToExpand: MutableList<Int> = mutableListOf()
        for (i in 0..<input.first().length) {
            if (input.fold(true) {acc, row -> acc && row[i] == '.'} ) {
                columnsToExpand.add(i)
            }
        }
        val galaxies: MutableList<Pair<Int, Int>> = mutableListOf()
        for (i in input.indices) {
            for (j in 0..<input.first().length) {
                if (input[i][j] != '.') {
                    galaxies.add(i to j)
                }
            }
        }
        var res = 0L
        for (i in galaxies.indices) {
            for (j in i + 1..<galaxies.size) {
                val g1 = galaxies[i]
                val g2 = galaxies[j]
                val expandedRowsBetween =
                    rowsToExpand.count { it in min(g1.first, g2.first) + 1..<max(g1.first, g2.first) }
                val expandedColumnsBetween =
                    columnsToExpand.count { it in min(g1.second, g2.second) + 1..<max(g1.second, g2.second) }
                res += distance(g1, g2) + expansionFactor * (expandedRowsBetween + expandedColumnsBetween)
            }
        }
        return res
    }

    fun part2(input: List<String>): Long = part1(input, 999999)
    val input = readInput("Day11")

    part1(input).println()
    part2(input).println()
}
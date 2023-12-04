import kotlin.math.min
fun powInt(n: Int, p: Int): Int =
    when(p) {
        0 -> 1
        else -> n * powInt(n, p - 1)
    }

fun parseInput(input: List<String>): List<List<Set<Int>>> =
       input.map { row ->
                   row.substringAfter(':')
                      .split('|')
                      .flatMap { cards ->
                                 cards.split(',')
                                      .map {card ->
                                            card.split(' ')
                                                .filterNot { it.isEmpty() }
                                                .map { num -> num.toInt() }
                                                .toSet()
                                            }
                                }
                 }

fun main() {
    fun part1(input: List<String>): Int {
        return parseInput(input).map { cards -> cards.first().intersect(cards.last()) }
                                .sumOf {set -> if (set.isNotEmpty()) powInt(2, set.size - 1) else 0}
    }

    fun part2(input: List<String>): Int {
        val parsedInput: List<List<Set<Int>>> = parseInput(input)
        val map: MutableMap<Int, Int> = mutableMapOf()
        parsedInput.indices.forEach { index -> map[index] = 1 }
        map.keys.forEach{ index ->
            val (winning, scratched) = parsedInput[index]
            val score = winning.intersect(scratched).size
            (index + 1..min(index + score, map.size - 1)).forEach { i ->
                map[i] = map.getValue(i) + map.getValue(index)
            }
        }
        return map.values.sum()
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

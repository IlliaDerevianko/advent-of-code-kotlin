abstract class Combination(private val s: String): Comparable<Combination> {
    abstract val cardRank: Map<Char, Int>
    val value = s.groupBy {it}.mapValues {it.value.size}.toMutableMap()

    override fun compareTo(other: Combination): Int {
        return if (value.size != other.value.size) {
            other.value.size - value.size
        } else {
            if (value.values.max() != other.value.values.max()) {
                value.values.max() - other.value.values.max()
            } else {  // same combination
                s.zip(other.s).map {(card1, card2) ->
                    cardRank.getValue(card1) - cardRank.getValue(card2)}.find {it != 0} ?: 0
            }
        }
    }
}

class Combination1(s: String): Combination(s) {
    override val cardRank = mapOf(
        '2' to 2,
        '3' to 3,
        '4' to 4,
        '5' to 5,
        '6' to 6,
        '7' to 7,
        '8' to 8,
        '9' to 9,
        'T' to 10,
        'J' to 11,
        'Q' to 12,
        'K' to 13,
        'A' to 14,
    )
}

class Combination2(s: String): Combination(s) {
    override val cardRank = mapOf(
        '2' to 2,
        '3' to 3,
        '4' to 4,
        '5' to 5,
        '6' to 6,
        '7' to 7,
        '8' to 8,
        '9' to 9,
        'T' to 10,
        'J' to 1,
        'Q' to 12,
        'K' to 13,
        'A' to 14,
    )
    init {
        val jokerCount = value.getOrDefault('J', 0)
        val mainCard = if (value.keys.filterNot {it == 'J'}.isNotEmpty())
            value.keys.filterNot { it == 'J' }.sortedWith(compareBy({value[it]}, {cardRank[it]})).last() else 'A'
        value[mainCard] = value.getOrDefault(mainCard, 0) + jokerCount
        value.remove('J')
    }
}

fun main() {
    fun part1(input: List<String>): Int =
        input.map { row -> row.split(' ') }.map { (card, bid) -> Combination1(card) to bid.toInt()}
            .sortedBy { (card, _) -> card }.map { it.second }.mapIndexed {index, num -> (index + 1) * num}.sum()

    fun part2(input: List<String>): Int =
        input.map { row -> row.split(' ') }.map { (card, bid) -> Combination2(card) to bid.toInt()}
            .sortedBy { (card, _) -> card }.map { it.second }.mapIndexed {index, num -> (index + 1) * num}.sum()

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

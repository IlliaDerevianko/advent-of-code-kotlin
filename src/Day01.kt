fun main() {
    fun part1(input: List<String>): Int =
        input.map { line -> line.filter { ch -> ch in '0'..'9' } }
             .sumOf { numString -> numString.first().digitToInt() * 10 + numString.last().digitToInt() }


    fun part2(input: List<String>): Int {
        val wordsToDigits = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        var r = 0

        for (line in input) {
            val ind1 = wordsToDigits.keys.map { line.indexOf(it) }. filter { it >= 0 }.minOrNull() ?: line.length
            val ind2 = wordsToDigits.keys.map { line.reversed().indexOf(it.reversed()) }
                                         .filter { it >= 0 }.minOrNull() ?: line.length
            val s1 = line.slice(0..<ind1)
            val s2 = line.slice(line.length - ind2..<line.length)

            val digitBefore: Char? = s1.find { it in '0'..'9' }
            val digitAfter : Char? = s2.findLast {it in '0'..'9'}

            val key1 = wordsToDigits.keys.find { it.startsWith(line.substringAfter(s1).take(2)) }
            r += (digitBefore?.digitToInt() ?: wordsToDigits.getValue(key1!!)) * 10
            val key2 = if (s2.isNotEmpty())
                            wordsToDigits.keys.find { it.endsWith(line.substringBeforeLast(s2).takeLast(3)) }
                       else wordsToDigits.keys.find { it.endsWith(line.takeLast(3)) }
            r += (digitAfter?.digitToInt() ?: wordsToDigits.getValue(key2!!))
        }
        return r
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

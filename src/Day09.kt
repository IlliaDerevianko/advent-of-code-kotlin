fun main() {
    val sequences = readInput("Day09").map { row -> row.split(' ').map { it.toInt() } }
    fun part1(): Int =
        sequences.sumOf { sequence ->
            var seq = sequence
            var prediction = 0
            while (!seq.all { it == 0 }) {
                prediction += seq.last()
                seq = seq.zipWithNext().map { it.second - it.first }
            }
            prediction
        }

    fun part2(): Int =
        sequences.sumOf { sequence ->
            var seq = sequence
            val firstElements: MutableList<Int> = mutableListOf()
            while (!seq.all { it == 0 }) {
                firstElements.add(seq.first())
                seq = seq.zipWithNext().map { it.second - it.first }
            }
            firstElements.reversed().fold(0) {acc: Int, num -> num - acc}
        }


    part1().println()
    part2().println()
}
fun isSpecialChar(i: Int, j: Int, engine: List<String>): Boolean =
    i in engine.indices && j in 0..<engine[i].length && engine[i][j] !in '0'..'9' && engine[i][j] != '.'

fun adjacentContainSpecialChar(num: String, row: Int, columnStart: Int, arr: List<String>): Boolean {
    val columnEnd: Int = columnStart + num.length - 1
    return (row - 1.. row + 1).flatMap { r -> (columnStart - 1..columnEnd + 1)
        .map { c -> isSpecialChar(r, c, arr) } }.any { it }
}

fun expand(coordinates: Pair<Int, Int>, engine: List<String>): Pair<Int, Int> {
    val (row, col) = coordinates
    val right = engine[row].slice(col..<engine[row].length).takeWhile { it in '0'..'9' }
    val left = engine[row].slice(0..<col).takeLastWhile { it in '0'..'9'}
    return (left + right).toInt() to col - left.length

}
fun gearRatio(row: Int, col: Int, engine: List<String>): Int {
    val adjacentNums = (row - 1.. row + 1).flatMap { r -> (col - 1..col + 1).map { c -> (r to c) } }
        .filter { (r, c) -> engine[r][c] in '0'..'9' }
        .map {pair -> expand(pair, engine)}.distinct()
        .map { it.first }
    return if (adjacentNums.size == 2) adjacentNums.first() * adjacentNums.last() else 0
}
fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for(i in input.indices) {
            var acc = ""
            var start = -1
            for(j in 0..<input[i].length) {
                if (input[i][j] in '0'..'9'){
                    if (acc.isEmpty()) {
                        start = j
                    }
                    acc += input[i][j]
                } else {
                    if (acc.isNotEmpty()) {  // we got our number in acc
                        if (adjacentContainSpecialChar(acc, i, start, input)) {
                            result += acc.toInt()
                        }
                    }
                    acc = ""
                }
            }
            if (acc.isNotEmpty()) { // taking care of the case if a line ends with a number
                if (adjacentContainSpecialChar(acc, i, start, input)) {
                    result += acc.toInt()
                }
            }

        }
        return result
    }
    fun part2(input: List<String>): Int {
        var result = 0
        input.indices.forEach {row ->
            val starColumns: List<Int> = input[row].mapIndexed {index, c -> (index to c)}
                                                 .filter { (_, c) -> c == '*' }.map { it.first }
            result += starColumns.sumOf { col -> gearRatio(row, col, input) }
        }
        return result
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

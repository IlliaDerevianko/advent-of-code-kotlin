enum class Direction {
    LEFT, RIGHT, UP, DOWN
}
fun main() {
    val directionsMap = mapOf(
        Direction.LEFT to setOf('-', 'F', 'L', 'S'),
        Direction.RIGHT to setOf('-', '7', 'J', 'S'),
        Direction.UP to setOf('|', '7', 'F', 'S'),
        Direction.DOWN to setOf('|', 'L', 'J', 'S')
    )
    val connectivesMap = mapOf(
        '|' to setOf(Direction.UP, Direction.DOWN),
        '-' to setOf(Direction.LEFT, Direction.RIGHT),
        'L' to setOf(Direction.UP, Direction.RIGHT),
        'J' to setOf(Direction.UP, Direction.LEFT),
        '7' to setOf(Direction.DOWN, Direction.LEFT),
        'F' to setOf(Direction.DOWN, Direction.RIGHT),
        'S' to setOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
    )
    fun findNextConnective(graph: List<String>, current: Pair<Int, Int>, previous: Pair<Int, Int>): Pair<Int, Int> {
        val (x, y) = current
        return mapOf(
            Direction.UP to (x - 1 to y), Direction.LEFT to (x to y - 1),
            Direction.DOWN to (x + 1 to y), Direction.RIGHT to (x to y + 1)
        ) .filter { (key, _) -> key in connectivesMap.getValue(graph[current.first][current.second])}
            .filter { (_, pair) -> pair.first in graph.indices && pair.second in 0..<graph.first().length }
            .filter { (key, pair) -> graph[pair.first][pair.second] in directionsMap.getValue(key) }
            .values
            .find {coordinates -> coordinates != previous }!!
    }
    fun part1(graph: List<String>): Int {
        val startRow: Int = graph.indexOfFirst { 'S' in it }
        val startColumn: Int = graph[startRow].indexOf('S')
        val start: Pair<Int, Int> = startRow to startColumn
        var cycleLength = 2
        var previousPos = start
        var currentPos = findNextConnective(graph, start, start)
        while(currentPos != start) {
            val temp = currentPos
            currentPos = findNextConnective(graph, currentPos, previousPos)
            previousPos = temp
            cycleLength++
        }
        return if (cycleLength % 2 == 0) cycleLength / 2 else (cycleLength - 1) / 2
    }

    part1(readInput("Day10")).println()

}
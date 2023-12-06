fun main() {
    fun part1(input: List<Pair<Long, Long>>): Int =
        input.map { (time, distance) -> List(time.toInt() + 1) { it }.count { t -> t * (time - t) > distance } }
             .fold(1) { a: Int, b: Int -> a * b }

    fun part2(time: Long, distance: Long): Int = part1(listOf(time to distance))

    val input = readInput("Day06")
    val (times, distances) = input.map { row ->
        row.substringAfter(":").split(" ").filterNot { s -> s == "" }.map {it.trim().toLong()} }
    val time = times.fold("") {acc, num -> acc + num.toString()}.toLong()
    val distance = distances.fold("") {acc, num -> acc + num.toString()}.toLong()
    part1(times.zip(distances)).println()
    part2(time, distance).println()

}

import kotlin.math.min
import kotlin.math.max

fun getSeeds(input: String): List<Long> {
    return input.substringBefore('\n').substringAfter(':')
                .split(' ').filterNot { it == "" }.map { s -> s.toLong() }
}

fun getMaps(input: String): List<List<Map<String, Long>>> {
    return input.substringAfter('\n').split("\n\n")
        .map { group ->
            group.substringAfter(':').split('\n').filterNot { s -> s.isEmpty() }
                .map {
                    it.split(' ')
                }
                .map { (fst, snd, trd) ->
                    mapOf("dest" to fst.toLong(), "org" to snd.toLong(), "range" to trd.toLong())
                }
        }
}
fun intervalIntersection(interval1: Pair<Long, Long>, interval2: Pair<Long, Long>): Pair<Long, Long>? {
    val (beg1, end1) = interval1
    val (beg2, end2) = interval2
    val left = max(beg1, beg2)
    val right = min(end1, end2)
    return if (left <= right) left to right else null
}

fun intervalSubtraction(
    interval1: Pair<Long, Long>,
    interval2: Pair<Long, Long>
): List<Pair<Long, Long>> {
    if (interval1.second < interval2.first || interval1.first > interval2.second){
        return listOf(interval1)
    }
    val leftResidue = if (interval1.first >= interval2.first) null else interval1.first to interval2.first - 1
    val rightResidue = if (interval1.second <= interval2.second) null else interval2.second + 1 to interval1.second
    return listOfNotNull(leftResidue, rightResidue)
}

fun intervalAddScalar(interval: Pair<Long, Long>, scalar: Long): Pair<Long, Long> {
    val (beg, end) = interval
    return beg + scalar to end + scalar
}

fun applyMap(interval: List<Pair<Long, Long>>, gardeningMap: List<Map<String, Long>>): List<Pair<Long, Long>> {
    val result: MutableList<Pair<Long, Long>> = mutableListOf()
    val residue: MutableList<Pair<Long, Long>> = interval.toMutableList()
    gardeningMap.forEach { triple ->
        val tempResidue: MutableList<Pair<Long, Long>> = mutableListOf()
        residue.forEach {interval1 ->
            val interval2 =
                triple.getValue("org") to triple.getValue("org") + triple.getValue("range") - 1
            val intersection = intervalIntersection(interval1, interval2)
            if (intersection != null) {
                result.add(intervalAddScalar(intersection,
                    triple.getValue("dest") - triple.getValue("org")))
            }
            tempResidue.addAll(intervalSubtraction(interval1, interval2))
        }
        residue.clear()
        residue.addAll(tempResidue)
        tempResidue.clear()
    }
    result.addAll(residue)
    return result
}

fun main() {
    fun part1(seeds: List<Long>, maps: List<List<Map<String, Long>>>): Long {
        return seeds.map {seed ->
            maps.fold(seed) {acc, gardeningMap ->
                gardeningMap.find { triple -> acc in
                        (triple.getValue("org")..<triple.getValue("org") + triple.getValue("range")) }
                            .let {acc + if (it != null) it.getValue("dest") - it.getValue("org") else 0 }
            }
        }.minOf { it }
    }

    fun part2(seeds: List<Long>, maps: List<List<Map<String, Long>>>): Long {
        val seedIntervals = seeds.zipWithNext().filterIndexed {index, _ -> index % 2 == 0}
            .map { (start, range) -> start to start + range - 1 }
        return maps.fold(seedIntervals) {acc, gardeningMap ->  applyMap(acc, gardeningMap)}.minOf { it.first }
    }

    val input = readInputAsString("Day05")
    val seeds = getSeeds(input)
    val maps = getMaps(input)
    part1(seeds, maps).println()
    part2(seeds, maps).println()
}



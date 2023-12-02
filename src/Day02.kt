import kotlin.math.max


enum class Colour {
    RED, GREEN, BLUE
}

class CubesSet(rawString: String) {
    private val maxCubes: Map<Colour, Int> = mapOf(
        Colour.RED to 12,
        Colour.GREEN to 13,
        Colour.BLUE to 14
    )
    private val cubes: MutableMap<Colour, Int> = mutableMapOf(
        Colour.RED to 0,
        Colour.GREEN to 0,
        Colour.BLUE to 0
    )

    //count number of cubes for each colour
    init {
        rawString.split(',').map { it.trim().split(' ') }
            .forEach {(count, colour) ->
                cubes[Colour.valueOf(colour.uppercase())] = count.toInt()
            }
    }

    fun isValid() = cubes.keys.all {cubes.getValue(it) <= maxCubes.getValue(it)}

    fun pairwiseMax(other: CubesSet): CubesSet {
        return CubesSet("${max(cubes.getValue(Colour.BLUE), other.cubes.getValue(Colour.BLUE))} blue," +
                                 "${max(cubes.getValue(Colour.RED), other.cubes.getValue(Colour.RED))} red," +
                                 "${max(cubes.getValue(Colour.GREEN), other.cubes.getValue(Colour.GREEN))} green"
        )
    }

    fun power(): Int = cubes.getValue(Colour.GREEN) * cubes.getValue(Colour.BLUE) * cubes.getValue(Colour.RED)
}

class Game(rawString: String){
    val id = rawString.substringBefore(':').split(' ').last().toInt()
    private val cubeSets = rawString.substringAfter(':').split(';').map { CubesSet(it) }
    val power: Int = cubeSets.fold(
        CubesSet("0 blue, 0 green, 0 red")
    ) { acc: CubesSet, cubesSet: CubesSet -> acc.pairwiseMax(cubesSet) }.power()

    fun isValid() = cubeSets.all { it.isValid() }
}
fun main() {
    fun part1(input: List<String>): Int = input.map { Game(it) }.filter {game: Game ->  game.isValid()}.sumOf { it.id }
    fun part2(input: List<String>): Int = input.sumOf { Game(it).power }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

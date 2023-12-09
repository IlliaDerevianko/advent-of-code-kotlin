class Node(val root: String, val left: String, val right: String) {
    override fun toString(): String = "$root($left)($right)"
}

fun gcd(a: Long, b: Long): Long =
    when(b) {
        0L -> a
        else -> gcd(b, a % b)
    }

fun lcm(a: Long, b: Long) = (a * b) / gcd(a, b)
fun main() {
    val input = readInput("Day08")
    val commands = input.first().trim()
    val nodes: List<Node> = input.subList(2, input.size).map {row -> row.split('=')}.map {(root, children) ->
        val (left, right) = children.substringAfter('(').substringBefore(')')
            .split(',').map { it.trim()}
        Node(root.trim(), left, right)
    }
    fun part1(commands: String, nodes: List<Node>, start: String = "AAA", isEnd: (String) -> Boolean = {it == "ZZZ"}): Int {
        var currentNode = nodes.find {it.root == start}!!
        var counter = 0
        while(!isEnd(currentNode.root)) {
            currentNode = if (commands[counter % commands.length] == 'L') nodes.find { it.root == currentNode.left }!! else nodes.find { it.root == currentNode.right }!!
            counter += 1
        }
        return counter
    }

    fun part2(commands: String, nodes: List<Node>): Long {
        val currentNodes = nodes.filter { it.root.endsWith('A') }
        val firstZ = currentNodes.map {node ->
            part1(commands, nodes, node.root) { it.endsWith('Z') }

        }
        print(firstZ)
        var product = 1L
        for (n in firstZ) {
            product *= n
        }
        val gcdFirstZ = firstZ.fold(firstZ.first().toLong()) {a, b -> lcm(a, b.toLong())}
        return (product / gcdFirstZ)
    }

    part1(commands, nodes).println()
    part2(commands, nodes).println()
}

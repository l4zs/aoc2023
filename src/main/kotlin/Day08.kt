import util.lcm

fun main() {
    Day08().run()
}

class Day08 : Day(8, "Haunted Wasteland", 2, 6L) {

    private var nodes: List<Node> = mutableListOf()
    private var instructions: String = ""

    data class Node(val name: String, val left: String, val right: String)

    private fun Node.left() = nodes.find { it.name == left }
    private fun Node.right() = nodes.find { it.name == right }

    override fun partOne(): Any {
        parse()
        return nodes.first{ it.name == "AAA" }.stepsUntil { it.name == "ZZZ" }
    }

    override fun partTwo(): Any {
        parse()
        return nodes.filter { it.name.endsWith('A') }.fold(1L) { acc, node ->
            lcm(acc, node.stepsUntil { it.name.endsWith('Z') }.toLong())
        }
    }

    private fun Node.stepsUntil(condition: (Node) -> Boolean): Int {
        var current = this
        var steps = 0
        while (!condition(current)) {
            when (instructions[steps % instructions.length]) {
                'L' -> current = current.left()!!
                'R' -> current = current.right()!!
            }
            steps++
        }
        return steps
    }

    private fun parse() {
        val input = inputText.split("\n\n")
        instructions = input[0]
        nodes = input[1].split("\n").map {
            val name = it.substringBefore("=").trim()
            val left = it.substringAfter("(").substringBefore(",").trim()
            val right = it.substringAfter(",").substringBefore(")").trim()
            Node(name, left, right)
        }
    }
}

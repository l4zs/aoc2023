import util.Position

fun main() {
    Day03().run()
}

class Day03 : Day(3, "Gear Ratios", 4361, 467835) {

    private val numberRegex = "\\d+".toRegex()

    data class Number(val xRange: IntRange, val y: Int, val value: Int) {
        fun position() = buildList {
            for (i in xRange) {
                add(Position(i, y))
            }
        }
    }

    data class Symbol(val x: Int, val y: Int, val symbol: Char) {
        fun position() = Position(x, y)
    }

    private fun List<String>.parseNumbersAndSymbols(): Pair<MutableList<Number>, MutableList<Symbol>> {
        val numbers = mutableListOf<Number>()
        val symbols = mutableListOf<Symbol>()
        forEachIndexed { y, line ->
            numberRegex.findAll(line).forEach { match ->
                numbers.add(Number(match.range, y, match.value.toInt()))
            }
            line.forEachIndexed { x, char ->
                if (!char.isDigit() && char != '.') {
                    symbols.add(Symbol(x, y, char))
                }
            }
        }
        return numbers to symbols
    }

    override fun partOne(): Any {
        return inputLines.parseNumbersAndSymbols().let { (numbers, symbols) ->
            numbers.filter {
                symbols.any { symbol -> symbol.position() in it.position().flatMap { it.adjacent() } }
            }.sumOf { it.value }
        }
    }

    override fun partTwo(): Any {
        return inputLines.parseNumbersAndSymbols().let { (numbers, symbols) ->
            symbols.filter { symbol ->
                symbol.symbol == '*' && numbers.count { number ->
                    symbol.position() in number.position().flatMap { it.adjacent() }
                } == 2
            }.map { symbol ->
                numbers.filter { number ->
                    symbol.position() in number.position().flatMap { it.adjacent() }
                }
            }.sumOf {
                it.map { number -> number.value }.reduce { acc, i -> acc * i }
            }
        }
    }
}

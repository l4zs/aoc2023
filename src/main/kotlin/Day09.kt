import util.numbers

fun main() {
    Day09().run()
}

class Day09 : Day(9, "Mirage Maintenance", 114L, 2L) {

    override fun partOne(): Any {
        return inputLines.history({ it.last() }, { acc, l -> acc + l })
    }

    override fun partTwo(): Any {
        return inputLines.history({ it.first() }, { acc, l -> l - acc })
    }

    fun List<String>.history(map: (List<Long>) -> Long, operation: (Long, Long) -> Long): Long {
        return this.map { it.numbers() }
            .map {
                val list = mutableListOf(it)
                while (list.last().any { it != 0L }) {
                    list += list.last().windowed(2, 1).map { (a, b) ->
                        b - a
                    }
                }
                list
            }.sumOf {
                it.reversed().map {
                    map(it)
                }.reduce(operation)
            }
    }
}

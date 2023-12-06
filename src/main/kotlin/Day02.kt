fun main() {
    Day02().run()
}

class Day02 : Day(2, "Cube Conundrum", 8, 2286) {

    enum class Color(val maxAmount: Int) {
        RED(12),
        GREEN(13),
        BLUE(14),
    }

    data class Cubes(val amount: Int, val color: Color)
    data class Handful(val cubes: List<Cubes>)
    data class Game(val id: Int, val handfuls: List<Handful>)

    override fun partOne(): Any {
        return inputLines.toGames().filter { game ->
            game.handfuls.none { handful ->
                handful.cubes.groupBy { it.color }.any { (color, cubes) ->
                    when (color) {
                        Color.RED -> cubes.sumOf { it.amount } > Color.RED.maxAmount
                        Color.GREEN -> cubes.sumOf { it.amount } > Color.GREEN.maxAmount
                        Color.BLUE -> cubes.sumOf { it.amount } > Color.BLUE.maxAmount
                    }
                }
            }
        }.sumOf { it.id }
    }

    override fun partTwo(): Any {
        return inputLines.toGames().map { game ->
            game.handfuls.flatMap { handful ->
                handful.cubes.map { cube ->
                    cube.amount to cube.color
                }
            }.groupBy { it.second }.map { (_, cubes) ->
                cubes.maxOf { it.first }
            }
        }.sumOf { (a, b, c) -> a * b * c }
    }

    fun List<String>.toGames(): List<Game> {
        return this.map {
            val (rawId, rawHandfuls) = it.split(":")
            val id = rawId.split(" ")[1].toInt()
            val handfuls = rawHandfuls.split(";").map { rawHandful ->
                val cubes = rawHandful.split(",").map { rawCube ->
                    val (_, amount, color) = rawCube.split(" ")
                    Cubes(amount.toInt(), Color.valueOf(color.uppercase()))
                }
                Handful(cubes)
            }
            Game(id, handfuls)
        }
    }
}

import kotlin.math.pow

fun main() {
    Day04().run()
}

class Day04: Day(4, "Scratchcards", 13, 30) {

    data class Scratchcard(val id: Int, val winningNumbers: Set<Int>, val numbers: Set<Int>) {
        fun getWinningNumbers(): Int {
            return winningNumbers.intersect(numbers).count()
        }
        fun getWinningAmount(): Int {
            return 2.0.pow(getWinningNumbers() - 1).toInt()
        }
    }

    override fun partOne(): Any {
        return inputLines.map { it.toScratchcard() }.sumOf { it.getWinningAmount() }
    }

    override fun partTwo(): Any {
        val scratchcards = inputLines.map { 1 to it.toScratchcard() }.toMutableList()
        scratchcards.forEachIndexed { index, pair ->
            val (amount, scratchcard) = pair
            (1..scratchcard.getWinningNumbers()).forEach { i ->
                if (index + i >= scratchcards.size) {
                    return@forEachIndexed
                }
                scratchcards[index + i] = scratchcards[index + i].copy(first = scratchcards[index + i].first + amount)
            }
        }
        scratchcards.forEach { println(it) }
        return scratchcards.sumOf { it.first }
    }

    private fun String.toScratchcard(): Scratchcard {
        val (rawId, rawOther) = this.split(":")
        val id = rawId.filter { it.isDigit() }.toInt()
        val (rawWinningNumbers, rawNumbers) = rawOther.split("|")
        val winningNumbers = rawWinningNumbers.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        val numbers = rawNumbers.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        return Scratchcard(id, winningNumbers, numbers)
    }
}

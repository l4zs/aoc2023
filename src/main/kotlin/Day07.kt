fun main() {
    Day07().run()
}

class Day07 : Day(7, "Camel Cards", 6440, 5905) {

    enum class Card {
        A, K, Q, J, T, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO, JOKER
    }

    enum class HandType {
        FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD
    }

    data class Hand(val cards: List<Card>, val bidAmount: Int, val joker: Boolean = false) {
        val type: HandType

        init {
            type = if (joker) {
                Card.entries.filter { it != Card.J && it != Card.JOKER }.minOf { card ->
                    val jokerCards = cards.map { if (it == Card.JOKER) card else it }
                    handType(jokerCards)
                }
            } else {
                handType(cards)
            }
        }

        private fun handType(cards: List<Card>): HandType {
            val counts = cards.groupingBy { it }.eachCount()
            val sortedCounts = counts.toList().sortedByDescending { it.second }

            return when (sortedCounts[0].second) {
                5 -> HandType.FIVE_OF_A_KIND
                4 -> HandType.FOUR_OF_A_KIND
                3 -> if (sortedCounts[1].second == 2) HandType.FULL_HOUSE else HandType.THREE_OF_A_KIND
                2 -> if (sortedCounts[1].second == 2) HandType.TWO_PAIR else HandType.ONE_PAIR
                else -> HandType.HIGH_CARD
            }
        }

        fun compareTo(other: Hand): Int {
            if (type.ordinal < other.type.ordinal) return 1
            if (type.ordinal > other.type.ordinal) return -1
            cards.zip(other.cards).forEach { (a, b) ->
                if (a.ordinal < b.ordinal) return 1
                if (a.ordinal > b.ordinal) return -1
            }
            return 0
        }
    }

    override fun partOne(): Any {
        return inputLines.parse(false).sortedWith(Hand::compareTo).map { it.bidAmount }.reduceIndexed { index, acc, bidAmount ->
            acc + bidAmount * (index + 1)
        }
    }

    override fun partTwo(): Any {
        return inputLines.parse(true).sortedWith(Hand::compareTo).map { it.bidAmount }.reduceIndexed { index, acc, bidAmount ->
            acc + bidAmount * (index + 1)
        }
    }

    fun List<String>.parse(joker: Boolean): List<Hand> {
        return map { line ->
            val cards = line.split(" ")[0].map { c ->
                when (c) {
                    'A' -> Card.A
                    'K' -> Card.K
                    'Q' -> Card.Q
                    'J' -> if (joker) Card.JOKER else Card.J
                    'T' -> Card.T
                    '9' -> Card.NINE
                    '8' -> Card.EIGHT
                    '7' -> Card.SEVEN
                    '6' -> Card.SIX
                    '5' -> Card.FIVE
                    '4' -> Card.FOUR
                    '3' -> Card.THREE
                    '2' -> Card.TWO
                    else -> throw Exception("Invalid card: $c")
                }
            }
            val bidAmount = line.split(" ")[1].toInt()
            Hand(cards, bidAmount, joker)
        }
    }
}

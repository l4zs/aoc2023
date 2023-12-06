fun main() {
    Day01().run()
}

class Day01 : Day(1, "Trebuchet?!", 142, 281) {
    private val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    private val regex = "([0-9])".toRegex()

    // use a positive lookahead to also match overlapping ("twone" -> "two" and "one")
    private val regexWords = "(?=([0-9]|${numbers.joinToString("|")}))".toRegex()

    override fun partOne(): Any {
        return inputLines
            .sumOf {
                calibrationValue(regex, it) { it }
            }
    }

    override fun partTwo(): Any {
        return inputLines
            .sumOf {
                calibrationValue(regexWords, it) { wordToNumber(it) }
            }
    }

    private fun calibrationValue(regex: Regex, input: String, block: (String) -> String): Int {
        return regex.findAll(input).map { it.groupValues[1] }.let { block(it.first()) + block(it.last()) }.toInt()
    }

    private fun wordToNumber(word: String): String {
        return if (word in numbers) {
            (numbers.indexOf(word) + 1).toString()
        } else {
            word
        }
    }
}

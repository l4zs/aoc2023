package util

internal val NUMBER_REGEX = """-?\d+""".toRegex()

fun String.numbers(): List<Long> {
    return NUMBER_REGEX.findAll(this).map { it.value.toLong() }.toList()
}

fun String.number(): Long {
    return NUMBER_REGEX.find(this)!!.value.toLong()
}

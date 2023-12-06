package util

fun String.numbers(): List<Long> {
    return this.filter { it.isDigit() || it.isWhitespace() }.trim().split("\\s+".toRegex()).map { it.toLong() }
}

fun String.number(): Long {
    return this.filter { it.isDigit() }.toLong()
}

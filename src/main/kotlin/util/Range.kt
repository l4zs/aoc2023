package util

fun LongRange.remove(range: LongRange): List<LongRange> {
    return when {
        range.first <= first -> {
            if (range.last >= last) return emptyList()
            listOf(range.last + 1..last)
        }

        range.last >= last -> {
            listOf(first..<range.first)
        }

        else -> {
            listOf(first..<range.first, range.last + 1..last)
        }
    }
}

fun IntRange.remove(range: IntRange): List<IntRange> {
    return when {
        range.first <= first -> {
            if (range.last >= last) return emptyList()
            listOf(range.last + 1..last)
        }

        range.last >= last -> {
            listOf(first..<range.first)
        }

        else -> {
            listOf(first..<range.first, range.last + 1..last)
        }
    }
}

package day05

import Day
import util.numbers
import util.remove

fun main() {
    Day05().run()
}

class Day05 : Day(5, "If You Give A Seed A Fertilizer", 35L, 46L) {

    data class Conversion(val destinationRange: LongRange, val sourceRange: LongRange)
    data class Conversions(val conversions: List<Conversion>) {
        fun convert(source: Long): Long {
            val conversion = conversions.firstOrNull { source in it.sourceRange }
            return conversion?.let { source + it.destinationRange.first - it.sourceRange.first } ?: source
        }

        fun convert(source: LongRange): List<LongRange> {
            var notConverted = listOf(source)
            return conversions.mapNotNull {
                val start = maxOf(it.sourceRange.first, source.first)
                val end = minOf(it.sourceRange.last, source.last)
                if (end < start) return@mapNotNull null
                val range = start..end
                notConverted = notConverted.flatMap { it.remove(range) }
                val diff = it.destinationRange.first - it.sourceRange.first
                (range.first() + diff)..(range.last() + diff)
            }.plus(notConverted)
        }
    }

    override fun partOne(): Any {
        val (seeds, conversions) = parseInput()
        return seeds.flatMap {
            conversions.fold(listOf(it)) { values, conversion ->
                values.map { conversion.convert(it) }
            }
        }.minOf { it }
    }

    override fun partTwo(): Any {
        val (seeds, conversions) = parseInput()
        return seeds.chunked(2).map { it[0]..<(it[0] + it[1]) }.flatMap {
            conversions.fold(listOf(it)) { values, conversion ->
                values.flatMap { conversion.convert(it) }
            }
        }.minOf { it.first }
    }

    private fun parseInput(): Pair<List<Long>, List<Conversions>> {
        val parts = inputText.split("\n\n")
        val seeds = parts[0].numbers()
        val conversions = parts.filter { it.contains("map") }.map {
            it.split("\n").drop(1).map { line ->
                val (destinationRangeStart, sourceRangeStart, rangeLength) = line.numbers()
                Conversion(
                    destinationRangeStart..<(destinationRangeStart + rangeLength),
                    sourceRangeStart..<(sourceRangeStart + rangeLength)
                )
            }.let { Conversions(it) }
        }
        return seeds to conversions
    }
}

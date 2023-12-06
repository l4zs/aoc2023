package day06

import Day
import util.number
import util.numbers

fun main() {
    Day06().run()
}

class Day06 : Day(6, "Wait For It", 288, 71503) {

    override fun partOne(): Any {
        val (times, distances) = inputLines.map { it.numbers() }
        val races = times zip distances
        return races.map { (time, distance) ->
            waysToBeat(time, distance)
        }.reduce { acc, i -> acc * i }
    }

    override fun partTwo(): Any {
        val (time, distance) = inputLines.map { it.number() }
        return waysToBeat(time, distance)
    }

    private fun waysToBeat(time: Long, distance: Long): Int {
        return (0..time).map {
            it * (time - it)
        }.count { it > distance }
    }
}

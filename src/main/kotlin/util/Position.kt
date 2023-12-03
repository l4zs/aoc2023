package util

data class Position(val x: Int, val y: Int) {
    fun north() = Position(x, y - 1)
    fun south() = Position(x, y + 1)
    fun east() = Position(x + 1, y)
    fun west() = Position(x - 1, y)
    fun northEast() = Position(x + 1, y - 1)
    fun northWest() = Position(x - 1, y - 1)
    fun southEast() = Position(x + 1, y + 1)
    fun southWest() = Position(x - 1, y + 1)
    fun adjacent() = listOf(north(), south(), east(), west(), northEast(), northWest(), southEast(), southWest())
    fun neighbors() = listOf(north(), south(), east(), west())
}

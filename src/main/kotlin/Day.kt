import java.io.File
import java.nio.file.Paths

abstract class Day(private val number: Int, private val title: String, private val testOne: Any, private val testTwo: Any) {

    private fun file(fileName: String): File = Paths.get(this.javaClass.getResource(fileName)!!.toURI()).toFile()

    val inputFile: File
        get() = if (isTest) {
            when (currentPart) {
                1 -> file("test1.txt")
                2 -> file("test2.txt")
                else -> throw Exception("No test input for part $number")
            }
        } else {
            when (currentPart) {
                1 -> file("input.txt")
                2 -> try {
                    file("input2.txt")
                } catch (e: Exception) {
                    file("input.txt")
                }
                else -> throw Exception("No input for part $number")
            }
        }
    val inputLines
        get() = inputFile.readLines()

    private var isTest = false
    private var currentPart = 0

    abstract fun partOne(): Any

    abstract fun partTwo(): Any

    fun run() {
        println("--- Day $number: $title ---")
        println()
        runTest(1, testOne, this::partOne)
        println()
        runTest(2, testTwo, this::partTwo)
    }

    private fun runTest(part: Int, expected: Any, function: () -> Any) {
        isTest = true
        currentPart = part
        val actual = function.invoke()
        println("Part $part:")
        if (expected != actual) {
            println("  Expected: $expected")
            println("  Actual: $actual")
        } else {
            isTest = false
            println("  Result: ${function.invoke()}")
        }
    }
}

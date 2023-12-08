package util

fun lcm(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

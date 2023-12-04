import kotlin.UnsupportedOperationException

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { "${it.first(Char::isDigit)}${it.last(Char::isDigit)}".toInt() }

    fun part2(input: List<String>): Int =
        input.sumOf { "${it.firstNumber()}${it.lastNumber()}".toInt() }
}

private val numbers = setOf(
    "one" to '1',
    "two" to '2',
    "three" to '3',
    "four" to '4',
    "five" to '5',
    "six" to '6',
    "seven" to '7',
    "eight" to '8',
    "nine" to '9'
)

private fun String.firstNumber(): Char {
    fold("") { acc, c ->
        numbers.find { (it, _) -> it == acc.takeLast(it.length) }?.let { (_, it) -> return it }
        if (c.isDigit()) return c
        acc + c
    }
    throw UnsupportedOperationException()
}

private fun String.lastNumber(): Char {
    foldRight("") { c, acc ->
        numbers.find { (it, _) -> it == acc.take(it.length) }?.let { (_, it) -> return it }
        if (c.isDigit()) return c
        c + acc
    }
    throw UnsupportedOperationException()
}




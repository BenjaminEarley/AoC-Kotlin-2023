fun main() {
    fun part1(input: List<String>): Int {
        val symbols = input.allPositionsAdjacentSymbols()
        return input.numbers().filter { it.positions.any(symbols::contains) }.sumOf(Number::value)
    }

    fun part2(input: List<String>): Int {
        val numbers = input.numbers()
        return input
            .gearZones()
            .mapNotNull { zones ->
                numbers
                    .filter { it.positions.any(zones.positions::contains) }
                    .takeIf { it.size > 1 }
                    ?.fold(1) { acc, number -> acc * number.value }
            }
            .sum()
    }
}

private data class Position(val row: Int, val column: Int)
private data class GearZone(val positions: Set<Position>)
private data class Number(val value: Int, val positions: List<Position>)

private val Position.adjacentPositions
    get() = buildList {
        for (rowOffset in -1..1) {
            for (columnOffset in -1..1) {
                if (rowOffset == 0 && columnOffset == 0) continue
                add(copy(row = row + rowOffset, column = column + columnOffset))
            }
        }
    }

private fun List<String>.allPositionsAdjacentSymbols(): Set<Position> = flatMapPositions { position, c ->
    if (!c.isDigit() && c != '.') position.adjacentPositions else emptyList()
}.toSet()

private fun List<String>.gearZones(): List<GearZone> = mapNotNullPositions { position, c ->
    if (!c.isDigit() && c != '.') GearZone(position.adjacentPositions.toSet()) else null
}

private fun String.toNumber(row: Int, column: Int) = Number(
    toInt(),
    indices.map { Position(row, column - it) }
)

private fun List<String>.numbers(): List<Number> = flatMapIndexed { row, s ->
    ("$s.").foldIndexed(emptyList<Number>() to "") { column, (numbers, buffer), c ->
        if (c.isDigit()) numbers to (buffer + c)
        else if (buffer.isNotEmpty()) (numbers + buffer.toNumber(row, column - 1)) to ""
        else numbers to buffer
    }.first
}

private inline fun <R : Any> List<String>.mapNotNullPositions(transform: (position: Position, Char) -> R?): List<R> =
    flatMapIndexed { row, s ->
        s.mapIndexedNotNull { column, c -> transform(Position(row, column), c) }
    }

private inline fun <R> List<String>.flatMapPositions(transform: (position: Position, Char) -> List<R>): List<R> =
    flatMapIndexed { row, s ->
        s.flatMapIndexed { column, c -> transform(Position(row, column), c) }
    }



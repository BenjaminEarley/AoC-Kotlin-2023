fun main() {
    fun part1(input: List<String>, possibleCubes: Cubes): Int =
        input
            .map(String::gameData)
            .filter { game ->
                game.rounds.all { round ->
                    possibleCubes.run { round.red <= red && round.green <= green && round.blue <= blue }
                }
            }
            .sumOf(Game::number)

    fun part2(input: List<String>): Int =
        input
            .map(String::gameData)
            .sumOf { game ->
                game.rounds.fold(Cubes()) { acc, cubes ->
                    Cubes(
                        red = maxOf(acc.red, cubes.red),
                        green = maxOf(acc.green, cubes.green),
                        blue = maxOf(acc.blue, cubes.blue)
                    )
                }.squared()
            }
}

private val gameRegex = Regex("""Game (?<game>\d+): (?<rounds>.*)""")
private fun String.gameData(): Game {
    val gameMatch = gameRegex.find(this)!!
    val gameNumber = gameMatch.groups["game"]!!.value
    val rounds = gameMatch.groups["rounds"]!!.value.split(';')
    return Game(
        number = gameNumber.toInt(),
        rounds = rounds.map(String::roundData)
    )
}

private fun String.roundData(): Cubes {
    fun List<String>.cubeCount(color: String) = find { it.endsWith(color) }?.takeWhile { it.isDigit() }?.toInt() ?: 0
    val roundInfo = split(",").map(String::trim)
    return Cubes(
        red = roundInfo.cubeCount("red"),
        green = roundInfo.cubeCount("green"),
        blue = roundInfo.cubeCount("blue")
    )
}

private data class Game(
    val number: Int,
    val rounds: List<Cubes>,
)

private data class Cubes(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
)

private fun Cubes.squared() = red * green * blue




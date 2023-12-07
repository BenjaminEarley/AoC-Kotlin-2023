fun main() {
    fun part1(input: List<String>): Int = input.sumOf(String::cardScore)

    fun part2(input: List<String>): Int = TODO()
}

private val cardRegex = Regex("""Card\s*(?<number>\d+): (?<winners>.*) \| (?<yours>.*)""")
private val numberSplitRegex = "\\s+".toRegex()

private fun String.cardScore(): Int {
    val cardMatch = cardRegex.find(this)!!
    val cardNumber = cardMatch.groups["number"]!!.value
    val winners = cardMatch.groups["winners"]!!.value.trim().split(numberSplitRegex).toSet()
    val yours = cardMatch.groups["yours"]!!.value.trim().split(numberSplitRegex).toSet()
    val winningNumbers = (winners intersect yours).size
    return calculateCardScore(winningNumbers)
}

fun calculateCardScore(winningNumbers: Int) =
    if (winningNumbers > 0) (1..<winningNumbers).fold(1) { acc, _ -> acc + acc }
    else 0
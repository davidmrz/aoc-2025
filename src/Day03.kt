fun main() {

    fun generateNumbers(bank: String, digits: Int): Sequence<Long> = sequence {
        if (digits == 1) {
            yieldAll(bank.map { it.digitToInt().toLong() })
        } else {
            for (i in 0..<bank.length - 1) {
                val first = bank[i]
                val others = generateNumbers(bank.drop(i + 1), digits - 1)
                for (other in others) {
                    yield("$first$other".toLong())
                }
            }
        }
    }

    fun part1(input: List<String>): Long {
        var sum = 0L
        for (bank in input) {
            sum += generateNumbers(bank, 2).max()
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        for (bank in input) {
            println(bank)
            sum += generateNumbers(bank, 12).max()
        }
        return sum
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

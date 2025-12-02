fun main() {

    fun String.rangeOfIds(): Iterable<String> {
        val (min, max) = split("-").map(String::toLong)
        return (min..max).map(Long::toString)
    }

    fun String.isMirrored(): Boolean {
        return if (length % 2 == 0) {
            // can be invalid only if it can be divided by 2
            val first = take(length / 2)
            val second = drop(length / 2)
            first == second
        } else {
            false
        }
    }

    fun String.generateSubstrings(size: Int): List<String> {
        return if (size <= length) {
            listOf(substring(0, size)) + substring(size).generateSubstrings(size)
        } else if (isNotEmpty()) {
            listOf(substring(0, length))
        } else {
            emptyList()
        }
    }

    fun String.isMadeUpOfSubsequence(): Boolean {
        val subsequences = (1..length.floorDiv(2)).map { substring(0, it) to substring(it) }

        for ((sub, rest) in subsequences) {
            val restSubs = rest.generateSubstrings(sub.length)
            val madeUpOfSubsequence = restSubs.all { it == sub }
            if (madeUpOfSubsequence) {
                return true
            }
        }

        return false
    }


    fun eval(case: String, isInvalid: String.() -> Boolean): Long {
        var sum = 0L
        for (id in case.rangeOfIds()) {
            if (id.isInvalid()) {
                sum += id.toLong()
            }

        }
        return sum
    }

    fun part1(input: List<String>): Long {
        var sum = 0L
        for (case in input) {
            sum += eval(case, String::isMirrored)
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        for (case in input) {
            sum += eval(case, String::isMadeUpOfSubsequence)
        }
        return sum
    }

    val input = readInput("Day02")
    part1(input.first().split(",")).println()
    part2(input.first().split(",")).println()
}

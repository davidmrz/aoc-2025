import kotlin.math.max

fun main() {

    fun parseInput(input: List<String>): Pair<List<LongRange>, List<Long>> {
        val ranges = mutableListOf<LongRange>()
        val ingredients = mutableListOf<Long>()

        var readingRanges = true
        for (line in input) {
            if (line.isBlank()) {
                readingRanges = false
                continue
            }
            if (readingRanges) {
                val numbers = line.split("-").map(String::toLong)
                ranges.add(numbers.let { (min, max) -> min..max })
            } else {
                ingredients.add(line.toLong())
            }
        }

        return Pair(ranges, ingredients)
    }

    fun part1(input: List<String>): Int {
        val (ranges, ingredients) = parseInput(input)

        var count = 0
        for (id in ingredients) {
            val fresh = ranges.any { id in it }
            if (fresh) {
                count += 1
            }
        }

        return count
    }

    tailrec fun mergeRanges(ranges: List<LongRange>): List<LongRange> {
        val result = mutableListOf<LongRange>()

        var i = 0
        val sorted = ranges.sortedBy { it.first } // sort by lower bound
        while (i < sorted.size) {
            val current = sorted[i]
            if (i + 1 < sorted.size) {
                val next = sorted[i + 1]
                if (current.last >= next.first) {
                    // overlap
                    result.add(current.first..max(current.last, next.last))
                    i += 2
                } else {
                    // no overlap
                    result.add(current)
                    i += 1
                }
            } else {
                // reach end, nothing to merge with
                result.add(current)
                i += 1
            }
        }

        // nothing else to merge
        return if (ranges == result) {
            ranges
        } else {
            mergeRanges(result)
        }
    }

    fun part2(input: List<String>): Long {
        val (ranges, _) = parseInput(input)
        val mergedRanges = mergeRanges(ranges)

        return mergedRanges.sumOf { it.last - it.first + 1 }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

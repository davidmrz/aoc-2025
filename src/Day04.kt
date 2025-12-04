fun main() {

    fun safeGet(row: Int, col: Int, grid: List<List<Boolean>>): Boolean {
        return if (row in grid.indices && col in grid[row].indices) {
            return grid[row][col]
        } else {
            false
        }
    }

    fun canAccess(row: Int, col: Int, grid: List<List<Boolean>>): Boolean {
        var count = 0

        if (safeGet(row - 1, col - 1, grid)) {
            count++
        }
        if (safeGet(row - 1, col, grid)) {
            count++
        }
        if (safeGet(row - 1, col + 1, grid)) {
            count++
        }
        if (safeGet(row, col + 1, grid)) {
            count++
        }
        if (safeGet(row + 1, col + 1, grid)) {
            count++
        }
        if (safeGet(row + 1, col, grid)) {
            count++
        }
        if (safeGet(row + 1, col - 1, grid)) {
            count++
        }
        if (safeGet(row, col - 1, grid)) {
            count++
        }

        return count < 4
    }

    fun remove(grid: List<List<Boolean>>): Pair<List<List<Boolean>>, Int> {
        val newGrid = grid.map { mutableListOf<Boolean>().apply { addAll(it) } }

        var count = 0
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                if (grid[row][col] && canAccess(row, col, grid)) {
                    newGrid[row][col] = false
                    count++
                }
            }
        }

        return Pair(newGrid, count)
    }

    fun parseGrid(input: List<String>): List<List<Boolean>> {
        return input.map { row -> row.map { it == '@' }.toList() }
    }

    fun part1(input: List<String>): Int {
        val grid = parseGrid(input)
        val (_, removed) = remove(grid)
        return removed
    }

    tailrec fun removeAll(grid: List<List<Boolean>>, acc: Int): Pair<List<List<Boolean>>, Int> {
        val (newGrid, removed) = remove(grid)

        return if (removed == 0) {
            Pair(newGrid, acc)
        } else {
            removeAll(newGrid, acc + removed)
        }
    }

    fun part2(input: List<String>): Int {
        val grid = parseGrid(input)
        val (_, removed) = removeAll(grid, 0)
        return removed
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

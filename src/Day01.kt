import kotlin.math.absoluteValue

data class Rotation(val finalPosition: Int, val zeroClicks: Int)

fun main() {

    fun change(position: Int, change: Int): Rotation {
        val fullTurns = change.absoluteValue.floorDiv(100)
        val rotation = change % 100
        val finalPosition = position + rotation

        return if (finalPosition < 0) {
            Rotation(
                finalPosition = 100 + finalPosition, // subtract from 100 the negative position
                zeroClicks = if (position == 0) fullTurns else fullTurns + 1 // count additional only if not starting from 0 as it was already counted in the final brand below
            )
        } else if (finalPosition > 99) {
            Rotation(
                finalPosition - 100, // reset to range 0, 100
                zeroClicks = if (position == 0) fullTurns else fullTurns + 1 // count additional only if not starting from 0 as it was already counted in the final brand below
            )
        } else {
            Rotation(
                finalPosition,
                zeroClicks = if (finalPosition == 0) fullTurns + 1 else fullTurns // one additional click if ending in 0
            )
        }
    }

    tailrec fun run(
        input: List<String>,
        currentPosition: Int,
        currentSecret: Int,
        calculateNewSecret: (Rotation, Int) -> Int
    ): Int {
        if (input.isEmpty()) {
            return currentSecret
        } else {
            val command = input.first()
            val direction = command.take(1)
            val rotation = command.drop(1).toInt()

            val tailCommands = input.drop(1)
            val appliedRotation = when (direction) {
                "R" -> change(currentPosition, rotation)
                "L" -> change(currentPosition, -rotation)
                else -> error("Invalid input")
            }

            val newSecret = calculateNewSecret(appliedRotation, currentSecret)

            return run(tailCommands, appliedRotation.finalPosition, newSecret, calculateNewSecret)
        }
    }

    fun part1(input: List<String>, position: Int, secret: Int): Int {
        return run(
            input,
            position,
            secret
        ) { rot, currentSecret ->
            // Count only when ending in 0
            if (rot.finalPosition == 0) currentSecret + 1 else currentSecret
        }
    }

    fun part2(input: List<String>, position: Int, secret: Int): Int {
        return run(
            input,
            position,
            secret
        ) { rot, currentSecret ->
            // Count all zero clicks
            currentSecret + rot.zeroClicks
        }
    }

    val input = readInput("Day01")
    part1(input, 50, 0).println()
    part2(input, 50, 0).println()
}

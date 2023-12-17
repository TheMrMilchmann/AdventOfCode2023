/*
 * Copyright (c) 2023 Leon Linhart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package days

import days.Layout.*
import utils.*
import utils.Direction.*
import kotlin.math.absoluteValue

fun main() {
    val grid = readInput().map(String::toList).toGrid()
    val layouts = grid.map { _, value ->
        when (value) {
            '|' -> NS
            '-' -> EW
            'L' -> NE
            'J' -> NW
            '7' -> SW
            'F' -> SE
            else -> null
        }
    }

    infix fun GridPos.moveTo(direction: Direction): GridPos? = when (direction) {
        N -> grid.shiftUp(this)
        W -> grid.shiftLeft(this)
        S -> grid.shiftDown(this)
        E -> grid.shiftRight(this)
    }

    val startPos = grid.positions.first { grid[it] == 'S' }

    val firstDirection = Direction.entries.first { direction ->
        -direction in ((startPos moveTo direction)?.let { layouts[it] } ?: return@first false)
    }

    val positions = generateSequence(startPos to firstDirection) { (pos, direction) ->
        val nextPos = (pos moveTo direction) ?: error("Should never happen")
        if (nextPos == startPos) return@generateSequence null

        val layout = layouts[nextPos] ?: error("Invalid direction: pos=$nextPos dir=$direction")
        nextPos to layout.dirs.first { it != -direction }
    }.toList()

    println("Part 1: ${positions.size / 2}")
    println("Part 2: ${positions.fold(0 to 0) { acc, (_, direction) -> direction.inc(acc) }.first.absoluteValue - (positions.size / 2) + 1}")
}

private enum class Layout(vararg val dirs: Direction) {
    NS(N, S),
    EW(E, W),
    NE(N, E),
    NW(N, W),
    SW(S, W),
    SE(S, E);

    operator fun contains(dir: Direction): Boolean = dir in dirs

}
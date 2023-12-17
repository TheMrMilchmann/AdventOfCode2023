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

import utils.*
import utils.Direction.*

fun main() {
    val grid = readInput().map(String::toList).toGrid()
    data class Origin(val pos: GridPos, val dir: Direction)

    infix fun Origin.reflectAt(mirror: GridPos): List<Origin> = when (grid[mirror]) {
        '|' -> when (dir) {
            N, S -> listOf(Origin(mirror, dir))
            else -> listOf(Origin(mirror, dir = N), Origin(mirror, dir = S))
        }
        '-' -> when (dir) {
            E, W -> listOf(Origin(mirror, dir))
            else -> listOf(Origin(mirror, dir = E), Origin(mirror, dir = W))
        }
        '/' -> when (dir) {
            N -> listOf(Origin(mirror, dir = E))
            E -> listOf(Origin(mirror, dir = N))
            S -> listOf(Origin(mirror, dir = W))
            W -> listOf(Origin(mirror, dir = S))
        }
        '\\' -> when (dir) {
            N -> listOf(Origin(mirror, dir = W))
            E -> listOf(Origin(mirror, dir = S))
            S -> listOf(Origin(mirror, dir = E))
            W -> listOf(Origin(mirror, dir = N))
        }
        else -> error("Unexpected character: ${grid[mirror]}")
    }

    val origins = ArrayDeque<Origin>()
    origins += Origin(pos = GridPos((-1).hPos, 0.vPos), dir = E)

    val energy = mutableMapOf<GridPos, Int>()

    val visited = HashSet<Origin>()
    while (origins.isNotEmpty()) {
        val origin = origins.removeFirst()
        if (!visited.add(origin)) continue

        val (pos, dir) = origin
        val beam = grid.beam(pos, when (dir) {
            N -> grid::shiftUp
            E -> grid::shiftRight
            S -> grid::shiftDown
            W -> grid::shiftLeft
        })

        origins += origin reflectAt (beam.takeWhileInclusive { grid[it] == '.' }.onEach { energy[it] = energy.getOrDefault(it, 0) + 1 }.lastOrNull()?.let { if (grid[it] == '.') null else it } ?: continue)
    }

    println("Part 1: ${energy.size}")
}
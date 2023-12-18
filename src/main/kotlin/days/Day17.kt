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
import java.util.HashSet
import java.util.PriorityQueue

fun main() {
    val grid = readInput().map { it.toList().map(Char::digitToInt) }.toGrid()
    data class Momentum(val pos: GridPos, val dir: Direction, val steps: Int)
    data class HeatState(val heat: Int, val momentum: Momentum)

    fun part1(): Int {
        val queue = PriorityQueue(compareBy(HeatState::heat))
        queue += HeatState(heat = 0, momentum = Momentum(pos = GridPos(0.hPos, 0.vPos), dir = E, steps = 0))

        val visited = HashSet<Momentum>()

        while (queue.isNotEmpty()) {
            val (heat, momentum) = queue.remove()

            if (!visited.add(momentum)) continue

            if (momentum.pos == grid.positions.last()) return heat

            for (dir in Direction.entries) {
                if (dir == -momentum.dir) continue

                val steps = if (dir == momentum.dir) momentum.steps + 1 else 1
                if (steps == 4) continue

                val position = when (dir) {
                    N -> grid::shiftUp
                    E -> grid::shiftRight
                    S -> grid::shiftDown
                    W -> grid::shiftLeft
                }(momentum.pos) ?: continue

                queue += HeatState(heat = heat + grid[position], Momentum(pos = position, dir = dir, steps = steps))
            }
        }

        error("End never reached")
    }

    println("Part 1: ${part1()}")
}
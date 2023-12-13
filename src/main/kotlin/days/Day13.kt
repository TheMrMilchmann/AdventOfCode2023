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

fun main() {
    val patterns = readInput().joinToString(separator = "\n").split("\n\n").map { it -> it.split("\n").map { it.toList() }.toGrid() }

    fun Grid<Char>.indexOfHorizontalReflection(): Int {
        for (i in 1 until height) {
            val up = buildList {
                for (j in verticalIndices.filter { it.intValue < i }.asReversed()) {
                    val start = GridPos(0.hPos, j)
                    add(start)
                    addAll(beam(start, ::shiftRight))
                }
            }

            val down = buildList {
                for (j in verticalIndices.filter { it.intValue >= i }) {
                    val start = GridPos(0.hPos, j)
                    add(start)
                    addAll(beam(start, ::shiftRight))
                }
            }

            val n = minOf(up.size, down.size)
            if (up.subList(0, n).map { this[it] } == down.subList(0, n).map { this[it] }) return i
        }

        return 0
    }

    fun Grid<Char>.indexOfVerticalReflection(): Int {
        for (i in 1 until width) {
            val left = buildList {
                for (j in horizontalIndices.filter { it.intValue < i }.asReversed()) {
                    val start = GridPos(j, 0.vPos)
                    add(start)
                    addAll(beam(start, ::shiftDown))
                }
            }

            val right = buildList {
                for (j in horizontalIndices.filter { it.intValue >= i }) {
                    val start = GridPos(j, 0.vPos)
                    add(start)
                    addAll(beam(start, ::shiftDown))
                }
            }

            val n = minOf(left.size, right.size)
            if (left.subList(0, n).map { this[it] } == right.subList(0, n).map { this[it] }) return i
        }

        return 0
    }

    println("Part 1: ${patterns.sumOf { pattern -> 100 * pattern.indexOfHorizontalReflection() + pattern.indexOfVerticalReflection() }}")
}
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

import utils.readInput

fun main() {
    val data = readInput()

    val seeds = data.first().removePrefix("seeds: ").split(' ').map(String::toLong)
    val mappings = data.drop(2).joinToString(separator = "\n").split("\n\n").map { map ->
        map.lines().drop(1).associate {
            val (destinationStart, sourceStart, length) = it.split(' ').map(String::toLong)
            sourceStart..<(sourceStart + length) to destinationStart..<(destinationStart + length)
        }
    }

    fun part1(): Long =
        seeds.minOf { seed ->
            mappings.fold(seed) { acc, map ->
                val (sourceRange, destRange) = map.entries.firstOrNull { acc in it.key } ?: return@fold acc
                destRange.first + (acc - sourceRange.first)
            }
        }

    println("Part 1: ${part1()}")
}
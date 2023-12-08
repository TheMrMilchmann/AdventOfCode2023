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
    val (instructions, mappings) = readInput().let { lines ->
        val instructions = lines.first()
        val mappings = lines.drop(2).associate { line ->
            val targets = line.substringAfter('(').removeSuffix(")").split(", ").let { it[0] to it[1] }
            line.substringBefore(' ') to targets
        }

        instructions to mappings
    }

    fun part1(): Int {
        var step = 0
        var pos = "AAA"

        val instructionsItr = iterator {
            while (true) {
                yieldAll(instructions.asIterable())
            }
        }

        while (pos != "ZZZ") {
            pos = when (val instruction = instructionsItr.next()) {
                'L' -> mappings[pos]!!.first
                'R' -> mappings[pos]!!.second
                else -> error("Unexpected instruction: $instruction")
            }

            step++
        }

        return step
    }

    println("Part 1: ${part1()}")
}
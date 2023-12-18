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
import kotlin.math.absoluteValue

fun main() {
    data class Instruction(val dir: Direction, val steps: Int)

    fun part1(): List<Instruction> =
        readInput().map { line ->
            val (dir, steps) = line.split(' ')

            val direction = when (dir) {
                "U" -> N
                "D" -> S
                "L" -> W
                "R" -> E
                else -> error("Unexpected direction: $dir")
            }

            Instruction(direction, steps.toInt())
        }

    fun part2(): List<Instruction> =
        readInput().map { line ->
            val instr = line.split(' ').last().removePrefix("(#").removeSuffix(")")
            val steps = instr.substring(startIndex = 0, endIndex = 5)
            val direction = when (val dir = instr.last()) {
                '0' -> E
                '1' -> S
                '2' -> W
                '3' -> N
                else -> error("Unexpected direction: $dir")
            }

            Instruction(direction, steps.toInt(radix = 16))
        }

    println("Part 1: ${part1().let { instructions -> instructions.fold(0L to 0L) { acc, (dir, steps) -> (1..steps).fold(acc) { iAcc, _ -> dir.inc(iAcc) } }.first.absoluteValue + (instructions.sumOf(Instruction::steps) / 2) + 1 }}")
    println("Part 2: ${part2().let { instructions -> instructions.fold(0L to 0L) { acc, (dir, steps) -> (1..steps).fold(acc) { iAcc, _ -> dir.inc(iAcc) } }.first.absoluteValue + (instructions.sumOf(Instruction::steps) / 2) + 1 }}")
}
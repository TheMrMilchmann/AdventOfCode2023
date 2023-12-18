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
    data class Instruction(val dir: Direction, val steps: Int, val color: String)

    val instructions = readInput().map { line ->
        val (dir, steps, color) = line.split(' ')

        val direction = when (dir) {
            "U" -> N
            "D" -> S
            "L" -> W
            "R" -> E
            else -> error("Unexpected direction: $dir")
        }

        Instruction(direction, steps.toInt(), color.removePrefix("(").removeSuffix(")"))
    }

    println("Part 1: ${instructions.fold(0 to 0) { acc, (dir, steps, _) -> (1..steps).fold(acc) { iAcc, _ -> dir.inc(iAcc) } }.first.absoluteValue + (instructions.sumOf(Instruction::steps) / 2) + 1}")
}
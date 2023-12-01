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
    val data = readInput()

    val tokenToDigit = mapOf(
        "1" to "1",
        "one" to "1",
        "2" to "2",
        "two" to "2",
        "3" to "3",
        "three" to "3",
        "4" to "4",
        "four" to "4",
        "5" to "5",
        "five" to "5",
        "6" to "6",
        "six" to "6",
        "7" to "7",
        "seven" to "7",
        "8" to "8",
        "eight" to "8",
        "9" to "9",
        "nine" to "9"
    )

    fun part2(): Int =
        data.map { line ->
            buildString {
                append(tokenToDigit.map { line.indexOf(it.key) to it.value }.filter { (index, _) -> index >= 0 }.minBy { (index, _) -> index }.second)
                append(tokenToDigit.map { line.lastIndexOf(it.key) to it.value }.maxBy { (index, _) -> index }.second)
            }
        }.map(String::toInt).sum()

    println("Part 1: ${data.map { "${it.first(Char::isDigit)}${it.last(Char::isDigit)}" }.map(String::toInt).sum() } ")
    println("Part 2: ${part2()}")
}
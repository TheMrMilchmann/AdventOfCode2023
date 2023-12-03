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
    val data: List<String> = readInput()
    val numbers = data.mapIndexed { lineIndex: Int, line: String ->
        var c = 0

        buildList {
            while (c < line.length) {
                if (!line[c].isDigit()) {
                    c++
                    continue
                }

                var number = 0

                val adjacentPositions = buildList {
                    if (c > 0) {
                        add(lineIndex to (c - 1))
                        if (lineIndex > 0) add(lineIndex - 1 to (c - 1))
                        if (lineIndex < data.lastIndex) add(lineIndex + 1 to (c - 1))
                    }

                    var ci = 0

                    do {
                        number = number * 10 + line[c + ci].digitToInt()

                        if (lineIndex > 0) add(lineIndex - 1 to (c + ci))
                        if (lineIndex < data.lastIndex) add(lineIndex + 1 to (c + ci))

                        ci++
                    } while (c + ci < line.length && line[c + ci].isDigit())

                    c += ci

                    if (c <= line.lastIndex) {
                        add(lineIndex to c)
                        if (lineIndex > 0) add(lineIndex - 1 to c)
                        if (lineIndex < data.lastIndex) add(lineIndex + 1 to c)
                    }
                }

                if (adjacentPositions.any { (y, x) -> val it = data[y][x]; it != '.' && !it.isDigit() }) {
                    add(number)
                }
            }
        }
    }.flatten()

    println("Part 1: ${numbers.sum()}")
}
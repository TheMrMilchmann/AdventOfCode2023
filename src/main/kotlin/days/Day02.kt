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
    data class Game(val gameId: Int, val draws: Set<Map<String, Int>>)

    val data = readInput().map { line ->
        val gameId = line.removePrefix("Game ").takeWhile { it != ':' }.toInt()
        val draws = line.substringAfter(':').split(";")
            .map {
                it.split(",").associate {
                    val (count, color) = it.trim().split(" ")
                    color to count.toInt()
                }
            }
            .toSet()

        Game(gameId, draws)
    }

    fun part1(): Int =
        data.filter { it.draws.all { it.getOrDefault("red", 0) <= 12 && it.getOrDefault("green", 0) <= 13 && it.getOrDefault("blue", 0) <= 14 } }
            .sumOf(Game::gameId)

    println("Part 1: ${part1()}")
}
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
    data class Card(val index: Int, val winningNumbers: Set<Int>, val myNumbers: Set<Int>)
    val cards = readInput().mapIndexed { index, line ->
        val (winningNumbers, myNumbers) = line.substringAfter(':').split('|')

        Card(
            index,
            winningNumbers.trim().split("\\s+".toRegex()).map(String::toInt).toSet(),
            myNumbers.trim().split("\\s+".toRegex()).map(String::toInt).toSet(),
        )
    }

    fun part1(): Int =
        cards.asSequence()
            .map { card -> card.myNumbers.count { it in card.winningNumbers } }
            .filter { it != 0 }
            .sumOf { 1 shl (it - 1) }

    fun part2(): Int {
        val currentCards = ArrayList(cards)
        var i = 0

        while (i < currentCards.size) {
            val card = currentCards[i]
            val matches = card.myNumbers.count { it in card.winningNumbers }

            i++

            for (ix in 0 until matches) {
                currentCards.add(i + ix, cards[card.index + 1 + ix])
            }
        }

        return currentCards.size
    }

    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}
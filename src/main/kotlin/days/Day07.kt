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
    data class Hand(val type: HandType, val cards: String) {
        override fun toString(): String = cards
    }

    fun part1(): Long {
        val entries = readInput().map { it.split(' ') }
            .map { (cards, bid) ->
                val groups = cards.groupBy { it }

                val hand = Hand(
                    type = when {
                        groups.size == 1 -> HandType.FIVE
                        groups.any { (_, values) -> values.size == 4 } -> HandType.FOUR
                        groups.any { (_, values) -> values.size == 3 } -> when {
                            groups.any { (_, values) -> values.size == 2 } -> HandType.FULL_HOUSE
                            else -> HandType.THREE
                        }
                        groups.count { (_, values) -> values.size == 2 } == 2 -> HandType.TWO_PAIR
                        groups.any { (_, values) -> values.size == 2 } -> HandType.ONE_PAIR
                        else -> HandType.HIGH_CARD
                    },
                    cards = cards
                )

                hand to bid.toLong()
            }

        val symbols = listOf('A', 'K', 'Q', 'J', 'T')
        val cmp: Comparator<Hand> = compareBy(Hand::type)
            .then { alpha, beta ->
                for (i in 0..<5) {
                    val a = alpha.cards[i]
                    val aI = symbols.indexOf(a)

                    val b = beta.cards[i]
                    val bI = symbols.indexOf(b)

                    when {
                        aI >= 0 && (bI < 0 || bI > aI) -> return@then 1
                        aI >= 0 && (bI < aI) -> return@then -1
                        aI < 0 && bI >= 0 -> return@then -1
                        else -> {
                            val x = a.compareTo(b)
                            if (x != 0) return@then x
                        }
                    }
                }

                0
            }

        return entries.sortedWith { (a, _), (b, _) -> cmp.compare(a, b) }.mapIndexed { index, (_, bid) -> (index + 1) * bid }.sum()
    }

    fun part2(): Long {
        val entries = readInput().map { it.split(' ') }
            .map { (cards, bid) ->
                val groups = cards.filter { it != 'J' }.groupBy { it }
                val jokers = cards.count { it == 'J' }

                val hand = Hand(
                    type = when {
                        jokers == 5 || groups.size == 1 -> HandType.FIVE
                        groups.any { (_, values) -> values.size == 4 } -> when (jokers) {
                            1 -> HandType.FIVE
                            else -> HandType.FOUR
                        }
                        groups.any { (_, values) -> values.size == 3 } -> when (jokers) {
                            2 -> HandType.FIVE
                            1 -> HandType.FOUR
                            else -> when {
                                groups.any { (_, values) -> values.size == 2 } -> HandType.FULL_HOUSE
                                else -> HandType.THREE
                            }
                        }
                        groups.count { (_, values) -> values.size == 2 } == 2 -> when (jokers) {
                            3 -> HandType.FIVE
                            2 -> HandType.FOUR
                            1 -> HandType.FULL_HOUSE
                            else -> HandType.TWO_PAIR
                        }
                        groups.any { (_, values) -> values.size == 2 } -> when (jokers) {
                            3 -> HandType.FIVE
                            2 -> HandType.FOUR
                            1 -> HandType.THREE
                            else -> HandType.ONE_PAIR
                        }
                        else -> when (jokers) {
                            4 -> HandType.FIVE
                            3 -> HandType.FOUR
                            2 -> HandType.THREE
                            1 -> HandType.ONE_PAIR
                            else -> HandType.HIGH_CARD
                        }
                    },
                    cards = cards
                )

                hand to bid.toLong()
            }

        val symbols = listOf('A', 'K', 'Q', 'T')
        val cmp: Comparator<Hand> = compareBy(Hand::type)
            .then { alpha, beta ->
                for (i in 0..<5) {
                    val a = alpha.cards[i]
                    val aI = symbols.indexOf(a)

                    val b = beta.cards[i]
                    val bI = symbols.indexOf(b)

                    when {
                        a == 'J' && b != 'J' -> return@then -1
                        a != 'J' && b == 'J' -> return@then 1
                        aI >= 0 && (bI < 0 || bI > aI) -> return@then 1
                        aI >= 0 && (bI < aI) -> return@then -1
                        aI < 0 && bI >= 0 -> return@then -1
                        else -> {
                            val x = a.compareTo(b)
                            if (x != 0) return@then x
                        }
                    }
                }

                0
            }

        return entries.sortedWith { (a, _), (b, _) -> cmp.compare(a, b) }.mapIndexed { index, (_, bid) -> (index + 1) * bid }.sum()
    }

    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

private enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE,
    FULL_HOUSE,
    FOUR,
    FIVE
}
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

    fun String.toHandType(): HandType {
        val groups = groupBy { it }
        return when {
            groups.size == 1 -> HandType.FIVE
            groups.any { (_, values) -> values.size == 4 } -> HandType.FOUR
            groups.any { (_, values) -> values.size == 3 } -> when {
                groups.any { (_, values) -> values.size == 2 } -> HandType.FULL_HOUSE
                else -> HandType.THREE
            }
            groups.count { (_, values) -> values.size == 2 } == 2 -> HandType.TWO_PAIR
            groups.any { (_, values) -> values.size == 2 } -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }
    }

    fun solve(symbols: String, replaceJokers: Boolean = false): Long =
        readInput().asSequence()
            .map { it.split(' ') }
            .map { (cards, bid) ->
                val hand = Hand(
                    type = if (replaceJokers) {
                        cards.replace('J', cards.filter { it != 'J' }.groupingBy { it }.eachCount().maxByOrNull(Map.Entry<Char, Int>::value)?.key ?: 'A')
                    } else {
                        cards
                    }.toHandType(),
                    cards = cards
                )

                hand to bid.toLong()
            }
            .sortedWith { (a, _), (b, _) ->
                var res = a.type.compareTo(b.type)
                if (res != 0) return@sortedWith res

                for (i in 0..<5) {
                    res = compareValuesBy(a, b) { it: Hand -> symbols.indexOf(it.cards[i]) }
                    if (res != 0) return@sortedWith res
                }

                error("Should never be reached")
            }
            .mapIndexed { index, (_, bid) -> (index + 1) * bid }
            .sum()

    println("Part 1: ${solve("23456789TJQKA")}")
    println("Part 2: ${solve("J23456789TQKA", replaceJokers = true)}")
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
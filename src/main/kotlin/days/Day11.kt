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
import kotlin.math.abs

fun main() {
    val data = readInput().map(String::toList).toGrid()

    val expandRows = data.verticalIndices.filter { y -> data.horizontalIndices.none { x -> data[x, y] == '#' } }
    val expandColumns = data.horizontalIndices.filter { x -> data.verticalIndices.none { y -> data[x, y] == '#' } }

    data class Pos(val x: Int, val y: Int) {

        infix fun distanceTo(other: Pos): Int =
            abs(other.x - x) + abs(other.y - y)

    }

    data class UnorderedPair(val first: Pos, val second: Pos) {

        override fun equals(other: Any?): Boolean = when {
            other === this -> true
            other is UnorderedPair -> (first == other.first && second == other.second) || (first == other.second && second == other.first)
            else -> false
        }

        override fun hashCode(): Int {
            val hash1 = first.hashCode()
            val hash2 = second.hashCode()
            return minOf(hash1, hash2) * 31 + maxOf(hash1, hash2)
        }

        override fun toString(): String =
            "[$first, $second]"

    }

    val galaxies = data.verticalIndices.flatMap { y ->
        data.horizontalIndices.mapNotNull { x ->
            if (data[x, y] == '.') return@mapNotNull null

            Pos(
                x = x.intValue + expandColumns.count { x > it },
                y = y.intValue + expandRows.count { y > it }
            )
        }
    }

    println("Part 1: ${galaxies.flatMap { a -> galaxies.mapNotNull { b -> if (a != b) UnorderedPair(a, b) else null } }.toSet().sumOf { (a, b) -> a distanceTo b }}")
}
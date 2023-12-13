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
import java.util.concurrent.ConcurrentHashMap

fun main() {
    val data = readInput().map { line -> line.split(" ").let { (str, groups) -> str to groups.split(',').map(String::toInt) } }

    data class State(val index: Int, val inGroup: Boolean, val lastGroup: Int, val lastGroupSize: Int)

    fun String.countMatches(
        groups: List<Int>,
        state: State,
        cache: MutableMap<State, Long> = ConcurrentHashMap()
    ): Long = cache.getOrPut(state) {
        when {
            state.index >= length -> if (groups.lastIndex == state.lastGroup && groups.last() == state.lastGroupSize) 1 else 0
            state.lastGroup >= groups.size || (state.lastGroup != -1 && ((state.lastGroupSize > groups[state.lastGroup]) || (!state.inGroup && state.lastGroupSize < groups[state.lastGroup]))) -> 0L
            else -> when (val token = this[state.index]) {
                '.' -> countMatches(groups, state.copy(index = state.index + 1, inGroup = false), cache)
                '#' -> countMatches(groups, state.copy(index = state.index + 1, inGroup = true, lastGroup = state.lastGroup + if (!state.inGroup) 1 else 0, lastGroupSize = if (state.inGroup) state.lastGroupSize + 1 else 1), cache)
                '?' -> countMatches(groups, state.copy(index = state.index + 1, inGroup = false), cache) +
                    countMatches(groups, state.copy(index = state.index + 1, inGroup = true, lastGroup = state.lastGroup + if (!state.inGroup) 1 else 0, lastGroupSize = if (state.inGroup) state.lastGroupSize + 1 else 1), cache)
                else -> error("Unexpected token: '$token'")
            }
        }
    }

    println("Part 1: ${data.sumOf { (str, groups) -> str.countMatches(groups, State(index = 0, inGroup = false, lastGroup = -1, lastGroupSize = 0)) }}")
    println("Part 2: ${data.map { (str, groups) -> buildList { for (i in 0..4) add(str) }.joinToString(separator = "?") to buildList { for (i in 0..4) addAll(groups) } }.sumOf { (str, groups) -> str.countMatches(groups, State(index = 0, inGroup = false, lastGroup = -1, lastGroupSize = 0)) }}")
}
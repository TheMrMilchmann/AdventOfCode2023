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
    val initializationSequence = readInput().single().split(",")

    fun hash(string: String): Int =
        string.fold(0) { acc, it -> ((acc + it.code) * 17) % 256 }

    fun part2(): Int {
        val boxes: MutableList<MutableMap<String, Int>> = MutableList(size = 256) { mutableMapOf() }

        for (seq in initializationSequence) {
            val (l, r) = seq.split("[=\\-]".toRegex())
            val box = boxes[hash(l)]

            if (r.isEmpty()) { // -
                box.remove(l)
            } else { // =
                box[l] = r.toInt()
            }
        }

        return boxes.withIndex().sumOf { (boxNumber, box) -> box.values.withIndex().sumOf { (slot, focalLength) -> (1 + boxNumber) * (slot + 1) * focalLength } }
    }

    println("Part 1: ${initializationSequence.map(::hash).sum()}")
    println("Part 1: ${part2()}")
}
package com.example.photoeditorpolishanything

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect

class Template_image {
    fun detectWhiteSpaces(bitmap: Bitmap): List<Rect> {
        val whiteSpaces = mutableListOf<Rect>()
        val width = bitmap.width
        val height = bitmap.height
        val visited = Array(width) { BooleanArray(height) { false } }

        for (x in 0 until width) {
            for (y in 0 until height) {
                if (!visited[x][y] && isWhitePixel(bitmap.getPixel(x, y))) {
                    val rect = findBoundingRect(bitmap, x, y, visited)
                    whiteSpaces.add(rect)
                }
            }
        }

        return whiteSpaces
    }

    private fun isWhitePixel(pixel: Int): Boolean {
        val r = Color.red(pixel)
        val g = Color.green(pixel)
        val b = Color.blue(pixel)
        return r > 240 && g > 240 && b > 240  // Consider a pixel white if it's close to (255, 255, 255)
    }

    private fun findBoundingRect(bitmap: Bitmap, startX: Int, startY: Int, visited: Array<BooleanArray>): Rect {
        val width = bitmap.width
        val height = bitmap.height
        var minX = startX
        var minY = startY
        var maxX = startX
        var maxY = startY

        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(Pair(startX, startY))
        visited[startX][startY] = true

        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst()

            minX = minOf(minX, x)
            minY = minOf(minY, y)
            maxX = maxOf(maxX, x)
            maxY = maxOf(maxY, y)

            val neighbors = listOf(Pair(x + 1, y), Pair(x - 1, y), Pair(x, y + 1), Pair(x, y - 1))
            for ((nx, ny) in neighbors) {
                if (nx in 0 until width && ny in 0 until height && !visited[nx][ny] && isWhitePixel(bitmap.getPixel(nx, ny))) {
                    queue.add(Pair(nx, ny))
                    visited[nx][ny] = true
                }
            }
        }

        return Rect(minX, minY, maxX, maxY)
    }

}
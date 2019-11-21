package com.example.holmi_production.money_counter_app.utils

import com.example.holmi_production.money_counter_app.model.entity.Balance

object Point {

    private val MIN_INTERVAL_SIZE = 3

    fun getLabelIndexes(balances: List<Balance>): List<Int> {
        var isUpDirection = true
        var tempElement = balances[0].amount
        val directionIndexes = arrayListOf<Int>()
        directionIndexes.add(0)
        for (i in 1 until balances.size) {
            val e = balances[i].amount
            if (isUpDirection) {
                if (e > tempElement) {
                    tempElement = e
                } else {
                    directionIndexes.add(i)
                    isUpDirection = !isUpDirection
                    tempElement = e
                }
            } else {
                if (e < tempElement)
                    tempElement = e
                else {
                    directionIndexes.add(i)
                    isUpDirection = !isUpDirection
                    tempElement = e
                }
            }
        }
        if (!directionIndexes.contains(balances.size))
            directionIndexes.add(balances.size)

        return populateInterval(directionIndexes)
    }

    private fun populateInterval(intervals: ArrayList<Int>): List<Int> {
        for (i in 1 until intervals.size) {
            val left = intervals[i - 1]
            val right = intervals[i]
            if ((right - left) > MIN_INTERVAL_SIZE) {
                val diff = (right - left) / MIN_INTERVAL_SIZE
                if (diff < 2) continue
                else {
                    val additionalPoint = getPoints(left, right)
                    intervals.addAll(additionalPoint)
                }
            }
        }
        return intervals.sorted()
    }

    private fun getPoints(leftBorder: Int, rightBorder: Int): ArrayList<Int> {

        val pointsCount = getPointsCount(rightBorder - leftBorder)
        val list = arrayListOf<Int>()
        var newPoint = leftBorder
        var step = (rightBorder - leftBorder)/pointsCount
        for (i in 1 until  pointsCount) {
            newPoint += step
            list.add(newPoint)
            step=(rightBorder-newPoint)/(pointsCount-i)
        }
        return list
    }

    private fun getPointsCount(intervalSize: Int): Int {
        return if (intervalSize <= 10) 2
        else intervalSize/10 + 2

    }

}
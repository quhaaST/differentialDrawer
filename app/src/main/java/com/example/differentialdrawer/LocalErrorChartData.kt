package com.example.differentialdrawer

class LocalErrorChartData : ChartDataBuilder() {
    override fun constructPoints(label: String) {
        val exactPoints = exactSolution.getSolutionList()
        val currentMethod = when (label) {
            "Euler" -> eulerMethodSolution
            "Improved Euler" -> improvedEulerMethodSolution
            else -> rungeKuttaMethodSolution
        }
        val methodPoints = currentMethod.getSolutionList()
        points.clear()
        for (i in 0 until exactPoints.size) {
            points.add(Pair(exactPoints[i].first, kotlin.math.abs(exactPoints[i].second - methodPoints[i].second)))
        }
    }
}
package com.example.differentialdrawer

class SolutionChartData : ChartDataBuilder() {
    override fun constructPoints(label: String) {
        points = when (label) {
            "Exact" -> exactSolution.getSolutionList()
            "Euler" -> eulerMethodSolution.getSolutionList()
            "Improved Euler" -> improvedEulerMethodSolution.getSolutionList()
            else -> rungeKuttaMethodSolution.getSolutionList()
        }
    }
}
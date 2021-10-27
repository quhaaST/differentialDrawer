package com.example.differentialdrawer


class GlobalErrorChartData : ChartDataBuilder() {
    private var finalStepNumber: Int = 0
    override fun constructPoints(label: String) {
        val mutableExact = ExactSolution()
        val x0 = exactSolution.getInitialX()
        val y0 = exactSolution.getInitialY()
        val X = exactSolution.getFinalX()
        val n0 = exactSolution.getInitialStep()

        val currentMethod = when (label) {
            "Euler" -> EulerMethodSolution()
            "Improved Euler" -> ImprovedEulerMethodSolution()
            else -> RungeKuttaMethodSolution()
        }

        mutableExact.updateParameters(x0, y0, X, n0)
        currentMethod.updateParameters(x0, y0, X, n0)
        points.clear()
        for (step in n0..finalStepNumber) {
            mutableExact.updateStepNumber(step)
            currentMethod.updateStepNumber(step)

            var maxError = 0.0f
            val exactPoints = mutableExact.getSolutionList()
            val methodPoints = currentMethod.getSolutionList()

            for (j in 0 until exactPoints.size) {
                val error = kotlin.math.abs(exactPoints[j].second - methodPoints[j].second)
                maxError = if (error > maxError) error else maxError
            }
            points.add(Pair(step.toFloat(), maxError))
        }
    }

    fun updateFinalStep(finalStepNumber: Int) {
        this.finalStepNumber = finalStepNumber
    }
}
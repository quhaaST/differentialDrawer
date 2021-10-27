package com.example.differentialdrawer

class RungeKuttaMethodSolution : NumericalSolutionBase() {
    override fun calculateSolutions() {
        solutions.clear()
        var currentX = initialX
        var lastPair = Pair(initialX, initialY)
        solutions.add(lastPair)
        while (currentX <= finalX - stepSize) {
            val k1 = initialEquation(currentX, lastPair.second)
            val k2 = initialEquation(currentX + stepSize / 2, lastPair.second + stepSize * k1 / 2)
            val k3 = initialEquation(currentX + stepSize / 2, lastPair.second + stepSize * k2 / 2)
            val k4 = initialEquation(currentX + stepSize, lastPair.second + stepSize * k3)
            var newValue = lastPair.second + stepSize / 6 * (k1 + 2 * k2 + 2 * k3 + k4)
            currentX += stepSize

            if (currentX - stepSize <= problemPoint && currentX > problemPoint) {
                newValue = ExactSolution().updateParameters(initialX, initialY, finalX, initialStepNumber).getSolution(currentX)
            }

            lastPair = Pair(currentX, newValue)
            solutions.add(lastPair)
        }
    }
}
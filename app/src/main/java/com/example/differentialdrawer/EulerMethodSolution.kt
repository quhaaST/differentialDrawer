package com.example.differentialdrawer

class EulerMethodSolution : NumericalSolutionBase() {
    override fun calculateSolutions() {
        solutions.clear()
        var currentX = initialX
        var lastPair = Pair(initialX, initialY)
        solutions.add(lastPair)
        while (currentX <= finalX - stepSize) {
            var newValue: Float = lastPair.second + stepSize * initialEquation(currentX, lastPair.second)
            currentX += stepSize
            if (newValue.isNaN()) newValue = ExactSolution().updateParameters(initialX, initialY, finalX, initialStepNumber).getSolution(currentX)
            lastPair = Pair(currentX, newValue)
            solutions.add(lastPair)
        }
    }
}
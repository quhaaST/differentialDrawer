package com.example.differentialdrawer

class EulerMethodSolution : NumericalSolutionBase() {
    override fun calculateSolutions() {
        solutions.clear()
        var currentX = initialX
        var lastPair = Pair(initialX, initialY)
        solutions.add(lastPair)
        for (i in 1 until initialStepNumber) {
            var newValue: Float = lastPair.second + stepSize * initialEquation(currentX,
                lastPair.second)
            currentX += stepSize

            if (currentX - stepSize <= problemPoint && currentX > problemPoint) {
                newValue = ExactSolution().updateParameters(initialX, initialY,
                    finalX, initialStepNumber).getSolution(currentX)
            }

            lastPair = Pair(currentX, newValue)
            solutions.add(lastPair)
        }
    }
}
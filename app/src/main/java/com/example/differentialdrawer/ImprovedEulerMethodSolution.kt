package com.example.differentialdrawer

class ImprovedEulerMethodSolution : NumericalSolutionBase() {
    override fun calculateSolutions() {
        solutions.clear()
        var currentX = initialX
        var lastPair = Pair(initialX, initialY)
        solutions.add(lastPair)
        while (currentX <= finalX - stepSize) {
            val yParam = lastPair.second + stepSize / 2 * initialEquation(currentX, lastPair.second)
            val deltaY = stepSize * initialEquation(currentX + stepSize / 2, yParam)
            var newValue = lastPair.second + deltaY
            currentX += stepSize
            if (currentX - stepSize <= problemPoint && currentX > problemPoint) {
                newValue = ExactSolution().updateParameters(initialX, initialY, finalX, initialStepNumber).getSolution(currentX)
            }
            lastPair = Pair(currentX, newValue)
            solutions.add(lastPair)
        }
    }
}
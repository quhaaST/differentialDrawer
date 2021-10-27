package com.example.differentialdrawer

import kotlin.math.ln
import kotlin.math.pow

class ExactSolution : MethodSolutionBase() {
    private var constant: Float = 0.0f

    fun updateParameters(x0: Float, y0: Float, X: Float, n0: Int): ExactSolution {
        this.initialX = x0
        this.finalX = X
        this.initialY = y0
        this.initialStepNumber = n0
        updateConstant()
        updateStepSize()
        calculateSolutions()
        return this
    }

    private fun updateConstant() {
        this.constant = ln(initialY / initialX + 1) / initialX
    }

    override fun updateStepNumber(stepNumber: Int) {
        updateParameters(initialX, initialY, finalX, stepNumber)
    }

    override fun calculateSolutions() {
        var currentX = initialX
        solutions.clear()
        while (currentX <= finalX) {
            val result = getSolution(currentX)
            solutions.add(Pair(currentX, result))

            currentX += stepSize
        }
    }

    fun getSolution(x: Float): Float {
        return x * (Math.E.pow(x * this.constant.toDouble()) - 1).toFloat()
    }
}
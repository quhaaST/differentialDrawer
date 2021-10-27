package com.example.differentialdrawer

import kotlin.math.ln

abstract class NumericalSolutionBase : MethodSolutionBase() {
    protected fun initialEquation(x: Float, y: Float): Float {
        return if ((x + y) / x <= 0 || x == problemPoint) Float.NaN
        else (1 + y / x) * ln((x + y) / x) + y / x
    }

    override fun updateStepNumber(stepNumber: Int) {
        updateParameters(initialX, initialY, finalX, stepNumber)
    }

    fun updateParameters(x0: Float, y0: Float, X: Float, n0: Int) {
        this.initialX = x0
        this.finalX = X
        this.initialY = y0
        this.initialStepNumber = n0
        updateStepSize()
        calculateSolutions()
    }
}
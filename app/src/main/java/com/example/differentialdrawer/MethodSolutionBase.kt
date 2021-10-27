package com.example.differentialdrawer

abstract class MethodSolutionBase {
    protected var initialX: Float = 0F
    protected var finalX: Float = 0F
    protected var initialY: Float = 0F
    protected var initialStepNumber: Int = 0
    protected var stepSize: Float = 0f
    protected var solutions: MutableList<Pair<Float, Float>> = mutableListOf()
    protected var problemPoint = 0f

    fun getSolutionList(): MutableList<Pair<Float, Float>> {
        return solutions
    }

    abstract fun updateStepNumber(stepNumber: Int)

    protected abstract fun calculateSolutions()

    protected fun updateStepSize() {
        this.stepSize = kotlin.math.abs(finalX - initialX) / (initialStepNumber - 1)
    }

    @JvmName("getInitialX1")
    fun getInitialX(): Float {
        return initialX
    }

    @JvmName("getInitialY1")
    fun getInitialY(): Float {
        return initialY
    }

    @JvmName("getFinalX1")
    fun getFinalX(): Float {
        return finalX
    }

    fun getInitialStep(): Int {
        return initialStepNumber
    }
}
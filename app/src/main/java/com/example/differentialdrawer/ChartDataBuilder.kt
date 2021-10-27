package com.example.differentialdrawer

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

abstract class ChartDataBuilder {
    protected var exactSolution = ExactSolution()
    protected var eulerMethodSolution = EulerMethodSolution()
    protected var improvedEulerMethodSolution = ImprovedEulerMethodSolution()
    protected var rungeKuttaMethodSolution = RungeKuttaMethodSolution()
    private var graphLineData = LineData()
    protected var points: MutableList<Pair<Float, Float>> = mutableListOf()

    fun appendLineData(label: String, color: Int): LineData {
        val lineDataSet = getLineDataSet(label)
        lineDataSet.color = color
        lineDataSet.setCircleColor(color)
        graphLineData.addDataSet(lineDataSet)
        return graphLineData
    }

    fun removeFromLineData(label: String): LineData {
        val removeDataSet = graphLineData.getDataSetByLabel(label, true)
        graphLineData.removeDataSet(removeDataSet)
        return graphLineData
    }

    private fun getLineDataSet(label: String): LineDataSet {
        val entriesList = constructEntriesList(label)
        val lineDataSet = LineDataSet(entriesList, label)
        lineDataSet.setDrawValues(false)
        return lineDataSet
    }

    private fun constructEntriesList(label: String): MutableList<Entry> {
        constructPoints(label)
        val entries: MutableList<Entry> = mutableListOf()
        for (i in 0 until points.size) {
            entries.add(Entry(points[i].first, points[i].second))
        }
        return entries
    }


    fun getLineData(): LineData {
        return graphLineData
    }

    fun updateMethods(x0: Float, y0: Float, X: Float, n0: Int) {
        this.exactSolution.updateParameters(x0, y0, X, n0)
        this.eulerMethodSolution.updateParameters(x0, y0, X, n0)
        this.improvedEulerMethodSolution.updateParameters(x0, y0, X, n0)
        this.rungeKuttaMethodSolution.updateParameters(x0, y0, X, n0)
    }

    protected abstract fun constructPoints(label: String)

    fun clearLineData() {
        graphLineData.clearValues()
    }

    @JvmName("getPoints1")
    fun getPoints(): MutableList<Pair<Float, Float>> {
        return points
    }
}
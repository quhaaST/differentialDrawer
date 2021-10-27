package com.example.differentialdrawer

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var etInitialX: EditText
    private lateinit var etInitialY: EditText
    private lateinit var etFinalX: EditText
    private lateinit var etInitialStep: EditText
    private lateinit var etFinalStep: EditText
    private lateinit var letInitialX: TextInputLayout
    private lateinit var letInitialY: TextInputLayout
    private lateinit var letFinalX: TextInputLayout
    private lateinit var letInitialStep: TextInputLayout
    private lateinit var letFinalStep: TextInputLayout
    private lateinit var btgSelectChart: MaterialButtonToggleGroup
    private lateinit var cgMethodsShow: ChipGroup
    private lateinit var cShowExact: Chip
    private lateinit var cShowEuler: Chip
    private lateinit var cShowImpEuler: Chip
    private lateinit var cShowRungeKutta: Chip
    private lateinit var tvYAxisLabel: TextView
    private lateinit var tvXAxisLabel: TextView
    private var graphContext = GraphContext.SOLUTION
    private var solutionDataBuilder = SolutionChartData()
    private var localErrorDataBuilder = LocalErrorChartData()
    private var globalErrorDataBuilder = GlobalErrorChartData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineChart = findViewById(R.id.lcChart)
        btgSelectChart = findViewById(R.id.btgButtonGroup)
        cShowExact = findViewById(R.id.cShowExact)
        cShowEuler = findViewById(R.id.cShowEuler)
        cShowImpEuler = findViewById(R.id.cShowImpEuler)
        cShowRungeKutta = findViewById(R.id.cShowRungeKutta)
        etInitialX = findViewById(R.id.etInitialX)
        etInitialY = findViewById(R.id.etInitialY)
        etFinalX = findViewById(R.id.etXBorder)
        etInitialStep = findViewById(R.id.etInitialStep)
        etFinalStep = findViewById(R.id.etFinalStep)
        cgMethodsShow = findViewById(R.id.chipGroup)
        letInitialX = findViewById(R.id.tilInitialXLayout)
        letInitialY = findViewById(R.id.tilInitialYLayout)
        letFinalX = findViewById(R.id.tilXBorderLayout)
        letInitialStep = findViewById(R.id.tilInitialStepLayout)
        letFinalStep = findViewById(R.id.tilFinalStepLayout)
        tvYAxisLabel = findViewById(R.id.tvYAxisLabel)
        tvXAxisLabel = findViewById(R.id.tvXAxisLabel)


        initChart()
        updateMethodsParameters()
        updateAllMethods(cgMethodsShow)
        setEditTextEnabled(letFinalStep, false)


        cShowExact.setOnCheckedChangeListener { buttonView, isChecked ->
            updateMethodVisibility(buttonView.text as String, if (isChecked) ChartAction.ADD else ChartAction.REMOVE)
        }

        cShowEuler.setOnCheckedChangeListener { buttonView, isChecked ->
            updateMethodVisibility(buttonView.text as String, if (isChecked) ChartAction.ADD else ChartAction.REMOVE)
        }

        cShowImpEuler.setOnCheckedChangeListener { buttonView, isChecked ->
            updateMethodVisibility(buttonView.text as String, if (isChecked) ChartAction.ADD else ChartAction.REMOVE)
        }

        cShowRungeKutta.setOnCheckedChangeListener { buttonView, isChecked ->
            updateMethodVisibility(buttonView.text as String, if (isChecked) ChartAction.ADD else ChartAction.REMOVE)
        }

        btgSelectChart.addOnButtonCheckedListener { _, checkedId, _ ->
            onContextUpdate(checkedId)
        }

        etInitialX.doAfterTextChanged {
            setEmptyError(letInitialX)
            updateMethodsParameters()
        }

        etInitialY.doAfterTextChanged {
            setEmptyError(letInitialY)
            updateMethodsParameters()
        }

        etFinalX.doAfterTextChanged {
            setEmptyError(letFinalX)
            updateMethodsParameters()
        }

        etInitialStep.doAfterTextChanged {
            setEmptyError(letInitialStep)
            updateMethodsParameters()
        }

        etFinalStep.doAfterTextChanged {
            setEmptyError(letFinalStep)
            updateMethodsParameters()
        }
    }

    private fun initChart() {
        lineChart.setPinchZoom(true)
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        lineChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        lineChart.description = null
    }

    private fun updateChartData(chartData: ChartDataBuilder) {
        lineChart.data = chartData.getLineData()
        lineChart.invalidate()
    }

    private fun updateMethodVisibility(label: String, action: ChartAction) {
        val currentDataBuilder: ChartDataBuilder = when (graphContext) {
            GraphContext.SOLUTION -> solutionDataBuilder
            GraphContext.LOCAL_ERROR -> localErrorDataBuilder
            GraphContext.GLOBAL_ERROR -> globalErrorDataBuilder
        }
        val color: Int = when (label) {
            "Exact" -> {
                ColorTemplate.COLORFUL_COLORS[0]
            }
            "Euler" -> {
                ColorTemplate.COLORFUL_COLORS[1]
            }
            "Improved Euler" -> {
                ColorTemplate.COLORFUL_COLORS[2]
            }
            else -> {
                ColorTemplate.COLORFUL_COLORS[3]
            }
        }

        if (action == ChartAction.ADD) {
            currentDataBuilder.appendLineData(label, color)
        } else {
            currentDataBuilder.removeFromLineData(label)
        }
        updateChartData(currentDataBuilder)
    }

    private fun updateAllMethods(chipGroup: ChipGroup) {
        solutionDataBuilder.clearLineData()
        localErrorDataBuilder.clearLineData()
        globalErrorDataBuilder.clearLineData()
        for (chipId in chipGroup.checkedChipIds) {
            println(chipId)
            if (chipId != null) {
                val currentChip = findViewById<Chip>(chipId)
                updateMethodVisibility(currentChip.text as String, ChartAction.ADD)
            }
        }
    }

    private fun onContextUpdate(checkedId: Int) {
        when (checkedId) {
            R.id.btnShowSolution -> {
                updateChipChecking(cShowExact, true)
                updateChartData(solutionDataBuilder)
                graphContext = GraphContext.SOLUTION
                tvXAxisLabel.text = "X"
                tvYAxisLabel.text = "Y"
                setEditTextEnabled(letFinalStep, false)
                setEditTextEnabled(letInitialX, true)
                setEditTextEnabled(letInitialY, true)
                setEditTextEnabled(letFinalX, true)
            }
            R.id.btnShowLocalError -> {
                updateChipChecking(cShowExact, false)
                updateChartData(localErrorDataBuilder)
                graphContext = GraphContext.LOCAL_ERROR
                tvXAxisLabel.text = "X"
                tvYAxisLabel.text = "E"
                setEditTextEnabled(letFinalStep, false)
                setEditTextEnabled(letInitialX, true)
                setEditTextEnabled(letInitialY, true)
                setEditTextEnabled(letFinalX, true)
            }
            R.id.btnShowGlobalError -> {
                updateChipChecking(cShowExact, false)
                updateChartData(globalErrorDataBuilder)
                graphContext = GraphContext.GLOBAL_ERROR
                tvXAxisLabel.text = "n"
                tvYAxisLabel.text = "E"
                setEditTextEnabled(letFinalStep, true)
                setEditTextEnabled(letInitialX, false)
                setEditTextEnabled(letInitialY, false)
                setEditTextEnabled(letFinalX, false)
            }
        }

        updateAllMethods(cgMethodsShow)
    }

    private fun updateChipChecking(chip: Chip, checkable: Boolean) {
        if (!checkable) {
            chip.isChecked = false
            chip.isClickable = false
        } else {
            chip.isClickable = true
            updateMethodVisibility(chip.text as String, ChartAction.ADD)
        }
        chip.isCheckable = checkable
    }

    private fun updateMethodsParameters() {
        val x0: Float
        val y0: Float
        val X: Float
        val n0: Int
        val N: Int

        if (!etInitialX.editableText.isNullOrEmpty() && !etInitialY.editableText.isNullOrEmpty()
                && !etFinalX.editableText.isNullOrEmpty() && !etInitialStep.editableText.isNullOrEmpty()
                && !etFinalStep.editableText.isNullOrEmpty()) {

                    try {
                        x0 = etInitialX.text.toString().toFloat()
                        y0 = etInitialY.text.toString().toFloat()
                        X = etFinalX.text.toString().toFloat()
                        n0 = etInitialStep.text.toString().toInt()
                        N = etFinalStep.text.toString().toInt()

                        if (setImproperStepError(x0, X, n0)) {
                            if (1 + y0 / x0 <= 0 || x0 == 0f) throw Exception()
                            solutionDataBuilder.updateMethods(x0, y0, X, n0)
                            localErrorDataBuilder.updateMethods(x0, y0, X, n0)
                            globalErrorDataBuilder.updateMethods(x0, y0, X, n0)
                            globalErrorDataBuilder.updateFinalStep(N)
                            updateAllMethods(cgMethodsShow)
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Invalid initial conditions!", Toast.LENGTH_SHORT).show()
                    }
            }
    }

    private fun setEmptyError(editTextLayout: TextInputLayout) {
        if (editTextLayout.editText?.editableText.isNullOrBlank()) {
            editTextLayout.error = "Empty!"
        } else {
            editTextLayout.error = null
        }
    }

    private fun setImproperStepError(x0: Float, X: Float, stepNumber: Int): Boolean {
        var valid = false
        if (stepNumber > 1) {
            val stepSize = kotlin.math.abs(X - x0) / (stepNumber - 1)
            if (stepSize < 1 && stepSize > 0) {
                valid = true
            }
        }

        if (valid) {
            letInitialStep.error = null
        } else {
            letInitialStep.error = "High step!"
        }
        return valid
    }

    private fun setEditTextEnabled(letLayout: TextInputLayout, enabled: Boolean) {
        letLayout.isEnabled = enabled
    }
}
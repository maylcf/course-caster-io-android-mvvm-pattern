package com.mayarafelix.tipcalculator.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mayarafelix.tipcalculator.R
import com.mayarafelix.tipcalculator.model.Calculator
import com.mayarafelix.tipcalculator.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(
    app: Application, val calculator: Calculator = Calculator()
) : ObservableViewModel(app) {

    private var lastTipCalculated = TipCalculation()

    var inputCheckAmount = ""
    var inputTipPercentage = ""

    val outputCheckAmount
        get() = getApplication<Application>().getString(
            R.string.dolar_amount,
            lastTipCalculated.checkAmount
        )
    val outputTipAmount
        get() = getApplication<Application>().getString(
            R.string.dolar_amount,
            lastTipCalculated.tipAmount
        )
    val outputTotalAmount
        get() = getApplication<Application>().getString(
            R.string.dolar_amount,
            lastTipCalculated.grandTotal
        )
    val locationName get() = lastTipCalculated.locationName

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tc: TipCalculation) {
        lastTipCalculated = tc
        notifyChange()
    }

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPercentage = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPercentage != null) {
            updateOutputs(calculator.calculateTip(checkAmount, tipPercentage))
            clearInputs()
        }
    }

    fun clearInputs() {
        inputCheckAmount = "0.00"
        inputTipPercentage = "0"
        notifyChange()
    }

    fun saveCurrentTip(name: String) {
        val tipToSave = lastTipCalculated.copy(locationName = name)
        calculator.saveTipCalculation(tipToSave)
        updateOutputs(tipToSave)
    }

    fun loadSavedTipCalculationSummary(): LiveData<List<TipCalculationSummaryItem>> {
        return Transformations.map(calculator.loadSavedTipCalculation()) { tipCalculationList ->
            tipCalculationList.map {
                TipCalculationSummaryItem(
                    it.locationName,
                    getApplication<Application>().getString(R.string.dolar_amount, it.grandTotal)
                )
            }
        }
    }

    fun loadTipCalculation(name: String) {
        val tc = calculator.loadTipCalculationByLocationName(name)

        if (tc != null) {
            inputCheckAmount = tc.checkAmount.toString()
            inputTipPercentage = tc.tipPercentage.toString()

            updateOutputs(tc)
            notifyChange()
        }
    }
}
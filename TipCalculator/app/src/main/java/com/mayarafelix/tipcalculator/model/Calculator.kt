package com.mayarafelix.tipcalculator.model

import androidx.lifecycle.LiveData
import java.math.RoundingMode

class Calculator(val repository: TipCalculationRepository = TipCalculationRepository()) {

    fun calculateTip(checkAmount: Double, tipPercentage: Int): TipCalculation {
        val tipAmount = checkAmount + (tipPercentage.toDouble() / 100.0).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        val total = checkAmount + tipAmount

        return TipCalculation(
            checkAmount = checkAmount,
            tipPercentage = tipPercentage,
            tipAmount = tipAmount,
            grandTotal = total
        )
    }

    fun saveTipCalculation(tc: TipCalculation) {
        repository.saveTipCalculation(tc)
    }

    fun loadTipCalculationByLocationName(locationName: String): TipCalculation? {
        return repository.loadTipCalculationByName(locationName)
    }

    fun loadSavedTipCalculation(): LiveData<List<TipCalculation>> {
        return repository.loadSavedTipCalculations()
    }
}
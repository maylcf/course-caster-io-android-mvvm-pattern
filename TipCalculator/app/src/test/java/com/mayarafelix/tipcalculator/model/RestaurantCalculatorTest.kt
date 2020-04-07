package com.mayarafelix.tipcalculator.model

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RestaurantCalculatorTest {

    lateinit var calculator: Calculator

    @Before
    fun setup() {
        calculator = Calculator()
    }

    @Test
    fun testCalculateTip() {
        val baseTipCalculation = TipCalculation(checkAmount = 10.00)
        val testVals = listOf(
            baseTipCalculation.copy(tipPercentage = 25, tipAmount = 2.5, grandTotal = 12.5),
            baseTipCalculation.copy(tipPercentage = 15, tipAmount = 1.5, grandTotal = 11.50),
            baseTipCalculation.copy(tipPercentage = 18, tipAmount = 1.8, grandTotal = 11.80)
        )

        testVals.forEach {
            assertEquals(it, calculator.calculateTip(it.checkAmount, it.tipPercentage))
        }
    }
}
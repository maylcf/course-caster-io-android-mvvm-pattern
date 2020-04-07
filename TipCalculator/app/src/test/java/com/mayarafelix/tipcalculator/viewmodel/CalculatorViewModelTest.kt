package com.mayarafelix.tipcalculator.viewmodel

import android.app.Application
import com.mayarafelix.tipcalculator.R
import com.mayarafelix.tipcalculator.model.Calculator
import com.mayarafelix.tipcalculator.model.TipCalculation
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CalculatorViewModelTest {

    lateinit var viewModel: CalculatorViewModel

    @Mock
    lateinit var mockCalculator: Calculator

    @Mock
    lateinit var application: Application

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        stubResource(0.00, "$0.00")
        viewModel = CalculatorViewModel(application, mockCalculator)
    }

    @Test
    fun testCalculateTip() {
        viewModel.inputCheckAmount = "10.00"
        viewModel.inputTipPercentage = "15"

        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.5, grandTotal = 11.5)
        `when`(mockCalculator.calculateTip(10.00, 15)).thenReturn(stub)

        stubResource(10.00, "$10.00")
        stubResource(1.50, "$1.50")
        stubResource(11.50, "$11.50")

        viewModel.calculateTip()

        //assertEquals(stub, viewModel.tipCalculation)
        assertEquals("$10.00", viewModel.outputCheckAmount)
        assertEquals("$1.50", viewModel.outputTipAmount)
        assertEquals("$11.50", viewModel.outputTotalAmount)
    }

    @Test
    fun testCalculateTip_BadTipPercentage() {
        viewModel.inputCheckAmount = "10.00"
        viewModel.inputTipPercentage = ""

        viewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyInt())
    }

    @Test
    fun testCalculateTip_BadCheckAmount() {
        viewModel.inputCheckAmount = ""
        viewModel.inputTipPercentage = "15"

        viewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyInt())
    }

    @Test
    fun testSaveCurrentTip() {

        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.5, grandTotal = 11.5)
        val stubLocationName = "Green Eggs"

        fun setupTipCalculation() {
            viewModel.inputCheckAmount = "10.00"
            viewModel.inputTipPercentage = "15"

            `when`(mockCalculator.calculateTip(10.00, 15)).thenReturn(stub)
        }

        setupTipCalculation()
        viewModel.calculateTip()

        viewModel.saveCurrentTip(stubLocationName)
        verify(mockCalculator).saveTipCalculation(stub.copy(locationName = stubLocationName))
        assertEquals(stubLocationName, viewModel.locationName)

    }

    private fun stubResource(given: Double, returnStub: String) {
        `when`(application.getString(R.string.dolar_amount, given)).thenReturn(returnStub)
    }
}
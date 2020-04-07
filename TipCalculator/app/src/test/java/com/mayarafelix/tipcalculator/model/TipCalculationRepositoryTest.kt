package com.mayarafelix.tipcalculator.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TipCalculationRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule() // needed to test live data

    lateinit var repository: TipCalculationRepository

    @Before
    fun setup() {
        repository = TipCalculationRepository()
    }

    @Test
    fun testSaveTip() {
        val tip = TipCalculation("Pancake World", 100.0, 25, 25.0, 125.0)

        repository.saveTipCalculation(tip)

        assertEquals(tip, repository.loadTipCalculationByName(tip.locationName))
    }

    @Test
    fun testLoadSavedTipCalculations() {
        val tip1 = TipCalculation("Pancake World", 100.0, 25, 25.0, 125.0)
        val tip2 = TipCalculation("China Taste", 50.0, 10, 5.0, 55.0)

        repository.saveTipCalculation(tip1)
        repository.saveTipCalculation(tip2)

        // will observe for the lifetime of this test
        repository.loadSavedTipCalculations().observeForever { tipCalculations ->
            assertEquals(2, tipCalculations?.size)
        }
    }
}
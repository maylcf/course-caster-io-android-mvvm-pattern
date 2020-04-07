package com.mayarafelix.tipcalculator.view

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.mayarafelix.tipcalculator.R
import org.junit.Rule
import org.junit.Test

class TipCalculatorActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testTipCalculator() {

        // Calculate Tip
        enter(checkAmount =  15.99, tipPercentage = 15)
        calculateTip()
        assertOutput(name = "", checkAmount = "$15.99", tipAmount = "$2.40", total = "$18.39")

        // Save Tip
        saveTip(name = "BBQ Max")
        assertOutput(name = "BBQ Max", checkAmount = "$15.99", tipAmount = "$2.40", total = "$18.39")

        // Clear outputs
        clearOutputs()
        assertOutput(name = "", checkAmount = "$0.00", tipAmount = "$0.00", total = "$0.00")

        // Load Tip
        loadTip("BBQ Max")
        assertOutput(name = "BBQ Max", checkAmount = "$15.99", tipAmount = "$2.40", total = "$18.39")
    }

    private fun enter(checkAmount: Double, tipPercentage: Int) {
        onView(withId(R.id.input_layout_check_amount)).perform(replaceText(checkAmount.toString()))
        onView(withId(R.id.input_layout_check_amount)).perform(replaceText(tipPercentage.toString()))
    }

    private fun calculateTip() {
        onView(withId(R.id.fab)).perform(click())
    }

    private fun assertOutput(name: String, checkAmount: String, tipAmount: String, total: String) {
        onView(withId(R.id.calculation_name)).check(matches(withText(name)))
        onView(withId(R.id.bill_amount)).check(matches(withText(checkAmount)))
        onView(withId(R.id.tip_amount)).check(matches(withText(tipAmount)))
        onView(withId(R.id.grand_total)).check(matches(withText(total)))
    }

    private fun clearOutputs() {
        enter(0.00, 0)
        calculateTip()
    }

    private fun saveTip(name: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())
        onView(withId(R.string.action_save)).perform(click())
        onView(withHint(R.string.save_hint)).perform(replaceText(name))
        onView(withText(R.string.action_save)).perform(click())
    }

    private fun loadTip(name: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())
        onView(withId(R.string.action_load)).perform(click())
        onView(withText(name)).perform(click())
    }
}
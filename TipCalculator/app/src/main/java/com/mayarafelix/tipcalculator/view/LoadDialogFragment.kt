package com.mayarafelix.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.mayarafelix.tipcalculator.R
import com.mayarafelix.tipcalculator.viewmodel.CalculatorViewModel
import kotlinx.android.synthetic.main.saved_tip_calculation_list.view.*

class LoadDialogFragment : DialogFragment() {

    interface Callback {
        fun onTipSelected(name: String)
    }

    private var loadTipCallback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadTipCallback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        loadTipCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = context?.let { ctx ->
            val editText = EditText(ctx)
            editText.id = SaveDialogFragment.viewId
            editText.hint = "Enter Location"

            AlertDialog.Builder(ctx)
                .setView(createView(ctx))
                .setNegativeButton(R.string.action_cancel, null)
                .create()
        }

        return dialog!!
    }

    private fun createView(context: Context): View {
        val layout = LayoutInflater
            .from(context)
            .inflate(R.layout.saved_tip_calculation_list, null)
            .recycler_saved_calculations

        layout.setHasFixedSize(true)
        layout.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val adapter = TipSummaryAdapter {
            loadTipCallback?.onTipSelected(it.locationName)
            dismiss()
        }

        layout.adapter = adapter

        val viewModel = ViewModelProviders.of(activity!!).get(CalculatorViewModel::class.java)
        viewModel

        return layout
    }
}
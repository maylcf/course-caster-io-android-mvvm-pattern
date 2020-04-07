package com.mayarafelix.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.mayarafelix.tipcalculator.R

class SaveDialogFragment: DialogFragment() {

    interface Callback {
        fun onSaveTip(name: String)
    }

    private var saveTipCallback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        saveTipCallback = context as? Callback // Cast safe
    }

    override fun onDetach() {
        super.onDetach()
        saveTipCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val saveDialog = context?.let {ctx ->
            val editText = EditText(ctx)
            editText.id = viewId
            editText.hint = context?.getString(R.string.save_hint)

            AlertDialog.Builder(ctx)
                .setView(editText)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_save) { _, _ -> onSave(editText)}
                .create()
        }

        return saveDialog!!
    }

    companion object {
        val viewId = View.generateViewId()
    }

    private fun onSave(editText: EditText) {
        val text = editText.text
        if (text.isNotEmpty()) {
            saveTipCallback?.onSaveTip(text.toString())
        }
    }
}

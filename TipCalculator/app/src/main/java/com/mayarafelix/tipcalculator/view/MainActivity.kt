package com.mayarafelix.tipcalculator.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.mayarafelix.tipcalculator.R
import com.mayarafelix.tipcalculator.databinding.ActivityMainBinding
import com.mayarafelix.tipcalculator.viewmodel.CalculatorViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SaveDialogFragment.Callback, LoadDialogFragment.Callback {

    lateinit var bindig: ActivityMainBinding
    lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)

        bindig = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindig.model = viewModel

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            viewModel.calculateTip()
        }
    }

    // Option Menu

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                showSaveDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSaveDialog() {
        val saveFragment = SaveDialogFragment()
        saveFragment.show(supportFragmentManager, "SaveDialog")
    }

    private fun showLoadDialog() {
        val saveFragment = LoadDialogFragment()
        saveFragment.show(supportFragmentManager, "LoadDialog")
    }

    // Interface

    override fun onSaveTip(name: String) {
        bindig.model?.saveCurrentTip(name)
        Snackbar.make(bindig.root, "Saved $name", Snackbar.LENGTH_SHORT).show()
    }

    override fun onTipSelected(name: String) {
        bindig.model?.loadTipCalculation(name)
        Snackbar.make(bindig.root, "Loaded $name", Snackbar.LENGTH_SHORT).show()
    }
}

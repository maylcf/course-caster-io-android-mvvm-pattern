package com.mayarafelix.tipcalculator.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mayarafelix.tipcalculator.R
import com.mayarafelix.tipcalculator.databinding.SavedTipCalculationItemBinding
import com.mayarafelix.tipcalculator.viewmodel.TipCalculationSummaryItem

class TipSummaryAdapter(val onItemSelected: (item: TipCalculationSummaryItem) -> Unit) :
    RecyclerView.Adapter<TipSummaryAdapter.TipSummaryViewHolder>() {

    private val list = mutableListOf<TipCalculationSummaryItem>()

    fun updateList(newList: List<TipCalculationSummaryItem>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipSummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<SavedTipCalculationItemBinding>(
            inflater, R.layout.saved_tip_calculation_list, parent, false
        )

        return TipSummaryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TipSummaryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class TipSummaryViewHolder(val binding: SavedTipCalculationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TipCalculationSummaryItem) {
            binding.item = item
            binding.root.setOnClickListener { onItemSelected(item) }
            binding.executePendingBindings() // update the view right away
        }
    }
}
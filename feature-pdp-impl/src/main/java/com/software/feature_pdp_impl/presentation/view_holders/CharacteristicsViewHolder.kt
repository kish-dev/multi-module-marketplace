package com.software.feature_pdp_impl.presentation.view_holders

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.software.feature_pdp_impl.R

class CharacteristicsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    var characteristicsTitleTV: AppCompatTextView? = null
    var characteristicsValueTV: AppCompatTextView? = null

    init {
        itemView.apply {
            characteristicsTitleTV = findViewById(R.id.characteristicsTitleTV)
            characteristicsValueTV = findViewById(R.id.characteristicsValueTV)
        }
    }

    fun bind(characteristic: Pair<String, String>) {
        characteristicsTitleTV?.text = characteristic.first
        characteristicsValueTV?.text = characteristic.second
    }
}
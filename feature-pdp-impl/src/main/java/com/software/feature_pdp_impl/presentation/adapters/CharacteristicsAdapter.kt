package com.software.feature_pdp_impl.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.software.core_utils.presentation.common.ViewTypes
import com.software.feature_pdp_impl.R
import com.software.feature_pdp_impl.presentation.view_holders.CharacteristicsViewHolder

class CharacteristicsAdapter : ListAdapter<Pair<String, String>, CharacteristicsViewHolder>(
    StringPairDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacteristicsViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.characteristics_item, parent, false)
        return CharacteristicsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacteristicsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return ViewTypes.CHARACTERISTICS
    }

    private class StringPairDiffUtil : DiffUtil.ItemCallback<Pair<String, String>>() {
        override fun areItemsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean =
            oldItem.first == newItem.first && oldItem.second.hashCode() == newItem.second.hashCode()

        override fun areContentsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean =
            oldItem == newItem

    }
}

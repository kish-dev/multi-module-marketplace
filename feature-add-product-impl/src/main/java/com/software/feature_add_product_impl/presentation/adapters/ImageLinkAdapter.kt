package com.software.feature_add_product_impl.presentation.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.software.core_utils.presentation.common.ViewTypes
import com.software.feature_add_product_impl.R
import com.software.feature_add_product_impl.presentation.view_holders.ImageLinkViewHolder
import com.software.feature_add_product_impl.presentation.view_objects.ImageLinkVO
import java.util.*

class ImageLinkAdapter : ListAdapter<ImageLinkVO, ImageLinkViewHolder>(
        ImageLinkDiffUtil()
    ) {

    companion object {
        private val TAG = ImageLinkAdapter::class.java.simpleName

        const val PAYLOAD_STRING_CHANGED = "payload_string_changed"
    }

    interface ImageLinkListener {
        fun onDeleteClick(position: Int, holder: ImageLinkViewHolder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageLinkViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.image_link_item, parent, false)
        return ImageLinkViewHolder(itemView, object : ImageLinkListener {
            override fun onDeleteClick(position: Int, holder: ImageLinkViewHolder) {
                val list: MutableList<ImageLinkVO> = currentList.toMutableList()
                list.removeAt(position)
                submitList(list)
            }

        }, RecyclerTextWatcher())
    }

    override fun onBindViewHolder(
        holder: ImageLinkViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            when (payloads[0]) {
                PAYLOAD_STRING_CHANGED -> {
                    holder.bind(getItem(position))
                    holder.textChangeListener.updateHolder(holder)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ImageLinkViewHolder, position: Int) {
        holder.textChangeListener.updateHolder(holder)
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return ViewTypes.IMAGE_LINK
    }

    override fun onViewAttachedToWindow(holder: ImageLinkViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.enableTextWatcher()
    }

    override fun onViewDetachedFromWindow(holder: ImageLinkViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.disableTextWatcher()
    }

    fun addEmptyLink() {
        val list: MutableList<ImageLinkVO> = currentList.toMutableList()
        list.add(ImageLinkVO(guid = UUID.randomUUID().toString(), imageLink = ""))
        submitList(list)
    }

    inner class RecyclerTextWatcher : TextWatcher {
        private var holder: ImageLinkViewHolder? = null

        fun updateHolder(holder: ImageLinkViewHolder) {
            this.holder = holder
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            val pos = holder?.layoutPosition ?: 0
            try {
                val list: MutableList<ImageLinkVO> = currentList.toMutableList()
                list[pos].imageLink = charSequence.toString()
                submitList(list)
            } catch (e: Exception) {
                Log.d(TAG, "onTextChanged Exception: ${e.printStackTrace()}")
            }

        }

        override fun afterTextChanged(editable: Editable) {
        }
    }

    private class ImageLinkDiffUtil : DiffUtil.ItemCallback<ImageLinkVO>() {
        override fun areItemsTheSame(oldItem: ImageLinkVO, newItem: ImageLinkVO): Boolean =
            oldItem.guid == newItem.guid


        override fun areContentsTheSame(oldItem: ImageLinkVO, newItem: ImageLinkVO): Boolean =
            oldItem == newItem

        override fun getChangePayload(oldItem: ImageLinkVO, newItem: ImageLinkVO): Any? {
            return when (oldItem.guid) {
                newItem.guid -> {
                    val diff = mutableListOf<String>()
                    if (oldItem.imageLink != newItem.imageLink) {
                        diff.add(PAYLOAD_STRING_CHANGED)
                    }
                    diff
                }
                else -> null
            }
        }
    }
}

package com.dummy.android.ui.main.taxes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dummy.android.R
import com.dummy.android.data.models.TaxRecord
import com.dummy.android.databinding.ListItemDummyTaxBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class DummyTaxesAdapter(private val clickListener: DummyTaxesListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DummyTaxesDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<TaxRecord>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.DummyTaxItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        @Suppress("USELESS_IS_CHECK")
        when (holder) {
            is ViewHolder -> {
                val dummyTaxItem = getItem(position) as DataItem.DummyTaxItem
                holder.bind(dummyTaxItem.record, clickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.DummyTaxItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(val binding: ListItemDummyTaxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaxRecord, clickListener: DummyTaxesListener) {
            binding.record = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemDummyTaxBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view =
                    layoutInflater.inflate(R.layout.list_item_dummy_tax_header, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }
}

class DummyTaxesDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class DummyTaxesListener(val clickListener: (record: TaxRecord) -> Unit) {
    fun onClick(record: TaxRecord) = clickListener(record)
}

sealed class DataItem {
    data class DummyTaxItem(val record: TaxRecord) : DataItem() {
        override val id = record.date
    }

    object Header : DataItem() {
        override val id = ""
    }

    abstract val id: String
}
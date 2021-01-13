package com.aashutosh.appdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aashutosh.appdemo.R
import com.aashutosh.appdemo.databinding.ListItemBinding
import com.aashutosh.appdemo.models.DemoModel
import kotlinx.android.extensions.LayoutContainer

class DemoListAdapter : RecyclerView.Adapter<DemoListAdapter.ViewHolder>() {

    private var mList: List<DemoModel>? = listOf()

    fun setData(list: List<DemoModel>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList!![position]
        holder.itemBinding.tvTitle.setText(item.title.capitalizeWords())
        holder.itemBinding.tvAdjective.setText(item.adjective.capitalizeWords())
        holder.itemBinding.tvCategory.setText(item.category.capitalizeWords())
        holder.itemBinding.tvPrice.setText("$"+item.price + "/-")
    }

    class ViewHolder(var itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {

        override val containerView: View?
            get() = itemBinding.root
    }

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

}
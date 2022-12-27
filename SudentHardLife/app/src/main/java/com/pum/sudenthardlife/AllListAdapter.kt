package com.pum.sudenthardlife

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class AllListAdapter(private val listList: MutableList<ListElement>) : RecyclerView.Adapter<AllListAdapter.AllListViewHolder>() {

    class AllListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = itemView.findViewById(R.id.titleList)
    }

    override fun getItemCount(): Int = listList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllListViewHolder {
        return AllListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllListViewHolder, position: Int) {
        val item = listList[position]
        holder.title.text = item.title

        holder.itemView.setOnClickListener{
            val action = AllListFragmentDirections.actionAllListFragmentToDetailListFragment(position)
            holder.itemView.findNavController().navigate(action)
        }
    }
}
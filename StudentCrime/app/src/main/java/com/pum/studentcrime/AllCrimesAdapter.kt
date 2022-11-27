package com.pum.studentcrime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class AllCrimesAdapter : RecyclerView.Adapter<AllCrimesAdapter.AllCrimesViewHolder>() {
    private val crimes = CrimesList.crimes

    class AllCrimesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = itemView.findViewById(R.id.titleCrime)
        val isSolved: ImageView = itemView.findViewById(R.id.imgIsSolved)
    }

    override fun getItemCount(): Int = crimes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCrimesViewHolder {
        return AllCrimesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_crime, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllCrimesViewHolder, position: Int) {
        val item = crimes[position]
        holder.title.text = item.title
        holder.isSolved.visibility = if (item.isSolved) View.VISIBLE else View.INVISIBLE

        holder.itemView.setOnClickListener{
            val crimeInfo = "Student ${item.id} committed a crime (${item.title}. ${item.description}. Resolved: ${item.isSolved})"

            val action = AllCrimesDirections.actionAllCrimesToDetailCrimeFragment(crimeInfo)
            holder.itemView.findNavController().navigate(action)
        }
    }


}
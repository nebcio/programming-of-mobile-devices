package com.pum.restcountriesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pum.restcountriesapp.databinding.CountryItemLayBinding

class CountryListAdapter(private val countryComparator: CountryComparator, private val flags: Boolean) :
    ListAdapter<Country, CountryListAdapter.CountryListHolder>(countryComparator) {

        class CountryListHolder(private val binding: CountryItemLayBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Country, flags: Boolean) {
                binding.country.text = item.name.common
                if (flags) {
                    Glide.with(binding.root).load(item.flags.png).into(binding.flag)
                    binding.flag.visibility = View.VISIBLE
                    binding.capital.visibility = View.GONE
                }
                else {
                    binding.capital.text = if (item.capital.isEmpty()) "None" else item.capital[0]
                    binding.flag.visibility = View.GONE
                    binding.capital.visibility = View.VISIBLE
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListHolder {
            return CountryListHolder(
                CountryItemLayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        override fun onBindViewHolder(holder: CountryListHolder, position: Int) {
            holder.bind(getItem(position), flags)
        }
}
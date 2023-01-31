package com.pum.restcountriesapp

import androidx.recyclerview.widget.DiffUtil

class CountryComparator : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.name.common == newItem.name.common
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }
}
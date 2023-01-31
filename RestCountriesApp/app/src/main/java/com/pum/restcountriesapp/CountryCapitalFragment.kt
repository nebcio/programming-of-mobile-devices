package com.pum.restcountriesapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pum.restcountriesapp.databinding.CountryCapitalFragmentLayBinding

class CountryCapitalFragment : Fragment() {
    private lateinit var binding: CountryCapitalFragmentLayBinding

    private val countryViewModel: CountryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CountryCapitalFragmentLayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryViewModel.getCountryList()

        val countriesAdapter = CountryListAdapter(CountryComparator(), false)
        binding.countryCapitalRecyclerView.apply {
            adapter = countriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        observeCountry(countriesAdapter)
    }

    private fun observeCountry(countryAdapter: CountryListAdapter) {
        countryViewModel.countriesList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { res ->
                        countryAdapter.submitList(res)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { Log.e("Flag tag", "Error occurred: $it") }
                }
                is Resource.Loading -> {
                    response.message?.let { Log.i("Flag tag", "Loading") }
                }
            }
        }
    }
}
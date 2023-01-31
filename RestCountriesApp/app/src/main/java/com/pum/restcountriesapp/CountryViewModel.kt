package com.pum.restcountriesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class CountryViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: CountryRepository = CountryRepository()
    private val countryList: MutableLiveData<Resource<CountryList>> = MutableLiveData()

    val countriesList: LiveData<Resource<CountryList>>
    get() = countryList

    fun getCountryList() = viewModelScope.launch {
        countryList.postValue(Resource.Loading())
        val response = repository.getCountries()
        countryList.postValue(handleCountryListResponse(response))
    }

    private fun handleCountryListResponse(response: Response<CountryList>)
            : Resource<CountryList> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(response.message())
    }
}
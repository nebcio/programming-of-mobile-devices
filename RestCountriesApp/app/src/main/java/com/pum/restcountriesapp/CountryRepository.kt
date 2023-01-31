package com.pum.restcountriesapp

class CountryRepository {
    suspend fun getCountries() = RetrofitInstance.api.getCountries()
}
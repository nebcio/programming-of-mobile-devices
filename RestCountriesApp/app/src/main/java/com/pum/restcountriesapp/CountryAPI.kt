package com.pum.restcountriesapp

import retrofit2.Response
import retrofit2.http.GET

interface CountryAPI {
    @GET("/v3.1/all?fields=name,capital,flags")
    suspend fun getCountries() : Response<CountryList>
}
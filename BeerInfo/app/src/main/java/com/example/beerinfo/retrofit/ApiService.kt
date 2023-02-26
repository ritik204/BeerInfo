package com.example.beerinfo.retrofit

import com.example.beerinfo.Model.BeerItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("beers")
    suspend fun getBeers(@Query("page") page:Int,
                 @Query("per_page") per_page: Int?): Response<List<BeerItem>>
}
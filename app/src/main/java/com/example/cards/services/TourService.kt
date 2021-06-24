package com.example.cards.services

import com.example.cards.services.dto.TourDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TourService {
    @GET("?p=SearchTour")
    fun SearchTour(
            @Query("search") search: String,
            @Query("date1") date1: String,
            @Query("date2") date2: String
    ): Call<List<TourDto>>
}
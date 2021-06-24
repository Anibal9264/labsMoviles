package com.example.cards.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cards.constantes.Constantes
import com.example.cards.services.TourService
import com.example.cards.services.dto.TourDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TourRepository {
    val Tours = MutableLiveData<List<TourDto>>()

    init {
        Tours.value = mutableListOf()
    }

    private fun getRetrofit(): TourService {
         return Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(TourService::class.java)
    }

      fun getFindTours(find: String, desde: String, hasta: String){
          getRetrofit().SearchTour(find, desde, hasta).enqueue(object : Callback<List<TourDto>> {
              override fun onResponse(call: Call<List<TourDto>>, response: Response<List<TourDto>>) {
                  Log.d("GetTours",response.body().toString())
                  Tours.value = response.body().orEmpty()
              }

              override fun onFailure(call: Call<List<TourDto>>, t: Throwable) {
                  Log.d("GetTours","Error al buscar Tours")
              }

          })
      }
}
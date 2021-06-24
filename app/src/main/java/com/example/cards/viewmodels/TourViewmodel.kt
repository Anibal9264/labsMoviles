package com.example.cards.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cards.repositories.TourRepository
import com.example.cards.services.dto.TourDto

class TourViewmodel : ViewModel() {
    var Tours = MutableLiveData<List<TourDto>>()
    var TourRepository = TourRepository()

    init {
        Tours = TourRepository.Tours
    }

    fun getTours(find: String, desde: String, hasta: String){
        TourRepository.getFindTours(find, desde, hasta)
    }

}
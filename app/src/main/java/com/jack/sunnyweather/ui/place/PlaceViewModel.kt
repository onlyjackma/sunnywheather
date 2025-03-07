package com.jack.sunnyweather.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jack.sunnyweather.logic.Repository
import com.jack.sunnyweather.logic.model.Place


class PlaceViewModel: ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = searchLiveData.switchMap{ query ->
        Repository.searchPlace(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

}
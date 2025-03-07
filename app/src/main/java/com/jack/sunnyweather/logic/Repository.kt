package com.jack.sunnyweather.logic
import android.util.Log
import androidx.lifecycle.liveData
import com.jack.sunnyweather.logic.model.Place
import com.jack.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {
    fun searchPlace(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
                Log.d("Repository","placeResponse=$placeResponse")
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is " +
                        "${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}
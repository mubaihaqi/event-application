package com.mubaihaqi.eventapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mubaihaqi.eventapplication.data.model.ListEventsItem
import com.mubaihaqi.eventapplication.data.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventViewModel : ViewModel() {
    private val _listEvents = MutableLiveData<List<ListEventsItem>>()
    val listEvents: LiveData<List<ListEventsItem>> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getEvents(active: Int = 1, keyword: String? = null, limit: Int = 40) {
        _isLoading.value = true
        _error.value = null

        val client = ApiConfig.getApiService().getEvents(active, keyword, limit)
        client.enqueue(object : Callback<com.mubaihaqi.eventapplication.data.model.EventResponse> {
            override fun onResponse(
                call: Call<com.mubaihaqi.eventapplication.data.model.EventResponse>,
                response: Response<com.mubaihaqi.eventapplication.data.model.EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listEvents.value = response.body()?.listEvents ?: emptyList()
                } else {
                    _error.value = response.message()
                }
            }

            override fun onFailure(
                call: Call<com.mubaihaqi.eventapplication.data.model.EventResponse>,
                t: Throwable
            ) {
                _isLoading.value = false
                _error.value = t.message
            }
        })
    }
}
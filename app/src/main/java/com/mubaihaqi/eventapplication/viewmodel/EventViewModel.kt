package com.mubaihaqi.eventapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mubaihaqi.eventapplication.data.model.EventResponse
import com.mubaihaqi.eventapplication.data.model.ListEventsItem
import com.mubaihaqi.eventapplication.data.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventViewModel : ViewModel() {
    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getUpcomingEvents() {
        _isLoading.value = true
        _error.value = null
        val client = ApiConfig.getApiService().getEvents(1, null, 40)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _upcomingEvents.value = response.body()?.listEvents ?: emptyList()
                    _error.value = null
                } else {
                    _upcomingEvents.value = emptyList()
                    _error.value = response.message()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _upcomingEvents.value = emptyList()
                _error.value = t.localizedMessage ?: "Unknown error"
            }
        })
    }

    fun getFinishedEvents() {
        _isLoading.value = true
        _error.value = null
        val client = ApiConfig.getApiService().getEvents(0, null, 40)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _finishedEvents.value = response.body()?.listEvents ?: emptyList()
                    _error.value = null
                } else {
                    _finishedEvents.value = emptyList()
                    _error.value = response.message()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _finishedEvents.value = emptyList()
                _error.value = t.localizedMessage ?: "Unknown error"
            }
        })
    }

    fun clearError() {
        _error.value = null
    }
}
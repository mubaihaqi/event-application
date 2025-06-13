package com.mubaihaqi.eventapplication.data.network

import com.mubaihaqi.eventapplication.data.model.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int,
        @Query("q") keyword: String? = null,
        @Query("limit") limit: Int = 40
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getEventDetail(
        @Path("id") id: String
    ): Call<EventResponse>
}